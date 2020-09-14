package com.github.brunoabdon.planinha.facade;

import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.TRACE;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdMappingException;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.modelo.Periodo;
import com.pivovarit.function.ThrowingBiFunction;
import com.pivovarit.function.ThrowingFunction;
import com.pivovarit.function.exception.WrappedException;

@ApplicationScoped
public class OperacaoFacade
        implements Facade<Operacao, Integer, Periodo, FatoVO>{

	@Inject
	Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    IdentifiableMapper<Fato, Integer, Operacao, Integer> mapper;

    @Inject
    IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapperConta;

    private BiFunction<Fato, Movimentacao, Lancamento> mapeaLancamento;
    private Function<Integer,Integer> contaKeyMapper;

    @PostConstruct
    void init() {
        final ThrowingBiFunction<
        Fato, Movimentacao, Lancamento, IdMappingException
     > mapeaChecked = this::extraiLancamento;

        this.mapeaLancamento = mapeaChecked.unchecked();

        final ThrowingFunction<Integer, Integer, IdMappingException>
            checkedContaKeyMapper = mapper::toKey;

        this.contaKeyMapper = checkedContaKeyMapper.uncheck();

    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Operacao cria(final Operacao operacao) throws BusinessException {
    	logger.logv(DEBUG, "Criando operação {0}.", operacao);

    	final FatoVO fatoVO = operacao.getFato();
        final Fato fato = new Fato(fatoVO.getDia(),fatoVO.getDescricao());

        final List<Integer> idsContas =
            operacao
                .getMovimentacoes()
                .stream()
                .map(Movimentacao::getConta)
                .map(ContaVO::getId)
                .distinct()
                .map(contaKeyMapper)
                .collect(toList());

        final long quantasContasEncontradas =
            em.createNamedQuery("Conta.porIds",Conta.class)
              .setParameter("ids", idsContas)
              .getResultStream()
              .count();

        if(quantasContasEncontradas != idsContas.size()) {
            //alguma das movimentacoes faz referencia a uma conta que não existe
            //no banco.
            //TODO Lancar uma entidadeInvalidaException, preferencialmente
            //dizendo quando foi a conta invalida.
            throw new BusinessException();
        }

        em.persist(fato);

        final List<Lancamento> lancamentos =
            extraiLancamentos(fato, operacao.getMovimentacoes());

        fato.setLancamentos(lancamentos);


        return mapper.toVO(fato);
    }

    private List<Lancamento> extraiLancamentos(
                final Fato fato, final List<Movimentacao> movimentacaos)
            throws BusinessException {

        final List<Lancamento> lancamentos;

        final Function<Movimentacao, Lancamento> movimentacaoPraLancamento =
            m -> mapeaLancamento.apply(fato, m);

        try {
            lancamentos =
                movimentacaos
                    .stream()
                    .map(movimentacaoPraLancamento)
                    .collect(toList());
        } catch (final WrappedException e) {
            final Throwable originalEx = e.getCause();
            if (originalEx instanceof BusinessException) {
                throw (BusinessException) originalEx;
            } else {
                throw new RuntimeException("Perdido.", originalEx);
            }
        }
        return lancamentos;
    }

    private Lancamento extraiLancamento(
            final Fato fato, final Movimentacao movimentacao)
                throws IdMappingException {

        final Integer idContaVO = movimentacao.getConta().getId();

        final Integer idConta = mapperConta.toKey(idContaVO);

        logger.logv(DEBUG, "Dando find na conta {0}",idConta);
        final Conta conta = em.find(Conta.class, idConta);

        return new Lancamento(fato,conta,movimentacao.getValor());

    }

    @Override
    public Operacao pega(final Integer id) throws EntidadeInexistenteException {
    	logger.logv(DEBUG, "Pegando operação de id {0}.", id);

    	final Fato fato = pega_(id);

        return mapper.toVO(fato);
    }

    private Fato pega_(final Integer id)
            throws IdMappingException, EntidadeInexistenteException {

        logger.logv(TRACE, "Pegando fato da operação de id {0}.", id);

        final Integer idFato = mapper.toKey(id);

        logger.logv(TRACE, "Pegando fato de id {0}.", id);

        final Fato fato = em.find(Fato.class, idFato);

        if(fato == null)
            throw new EntidadeInexistenteException(Operacao.class, id);
        return fato;
    }

    @Override
    public List<Operacao> lista(final Periodo periodo) {
    	logger.logv(DEBUG, "Listando operações por {0}.", periodo);

    	return
        	em.createNamedQuery("Fato.porPeriodo", Fato.class)
              .setParameter("dataInicio", periodo.getDataMinima())
              .setParameter("dataFim", periodo.getDataMaxima())
              .getResultStream()
              .map(mapper::toVOSimples)
              .collect(toList());
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Operacao atualiza(final Integer key, final FatoVO atualizacao)
            throws EntidadeInexistenteException, BusinessException {

        logger.logv(
            DEBUG, "Atualizando operação de id {0} com {1}.", key, atualizacao
        );

        final Fato fato = pega_(key);

        logger.logv(TRACE, "Atualizando fato {0} com {1}.", fato, atualizacao);

        fato.setDia(atualizacao.getDia());
        fato.setDescricao(atualizacao.getDescricao());

    	return mapper.toVO(fato);
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer key)
    		throws EntidadeInexistenteException, BusinessException {
    	logger.logv(DEBUG, "Deletando operação de id {0}.", key);

    	final Fato fato = pega_(key);

    	logger.logv(TRACE, "Deletando fato {0}.", fato);

    	em.remove(fato);
    }
}
