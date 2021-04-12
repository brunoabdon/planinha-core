package com.github.brunoabdon.planinha.facade;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdMappingException;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.planinha.dal.ContaRepository;
import com.github.brunoabdon.planinha.dal.FatoRepository;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.pivovarit.function.ThrowingBiFunction;
import com.pivovarit.function.ThrowingFunction;
import com.pivovarit.function.exception.WrappedException;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Setter(PACKAGE)
public class OperacaoFacade
        implements Facade<Operacao, Integer, Periodo, FatoVO>{

    @Autowired
    private IdentifiableMapper<Fato, Integer, Operacao, Integer> mapper;

    @Autowired
    private IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapperConta;

    @Autowired
    private ContaRepository contaRepo;

    @Autowired
    private FatoRepository fatoRepo;

    @Setter(value = NONE)
    private BiFunction<Fato, Movimentacao, Lancamento> mapeaLancamento;

    @Setter(value = NONE)
    private Function<Integer,Integer> contaKeyMapper;

    @PostConstruct
    void init() {
        final ThrowingBiFunction<
        Fato, Movimentacao, Lancamento, EntidadeInexistenteException
     > mapeaChecked = this::extraiLancamento;

        this.mapeaLancamento = mapeaChecked.unchecked();

        final ThrowingFunction<Integer, Integer, IdMappingException>
            checkedContaKeyMapper = mapper::toKey;

        this.contaKeyMapper = checkedContaKeyMapper.uncheck();
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public Operacao cria(final Operacao operacao) throws BusinessException {
    	log.debug("Criando operação {}.", operacao);

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
            contaRepo.countByIdIn(idsContas);

        if(quantasContasEncontradas != idsContas.size()) {
            //alguma das movimentacoes faz referencia a uma conta que não existe
            //no banco.
            //TODO Lancar uma entidadeInvalidaException, preferencialmente
            //dizendo quando foi a conta invalida.
            throw new BusinessException();
        }

        final Fato fatoCriado = fatoRepo.save(fato);

        final List<Lancamento> lancamentos =
            extraiLancamentos(fato, operacao.getMovimentacoes());

        fatoCriado.setLancamentos(lancamentos);

        return mapper.toVO(fatoCriado);
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
                throws EntidadeInexistenteException {

        final Integer idContaVO = movimentacao.getConta().getId();

        final Integer idConta = mapperConta.toKey(idContaVO);

        log.debug("Dando find na conta {}",idConta);

        return
            contaRepo
                .findById(idConta)
                .map(c -> new Lancamento(fato,c,movimentacao.getValor()))
                .orElseThrow(
                    () -> new EntidadeInexistenteException(
                        Conta.class,idConta
                    )
                );

    }

    @Override
    public Operacao pega(final Integer id) throws EntidadeInexistenteException {
    	log.debug("Pegando operação de id {}.", id);

    	final Fato fato = pega2(id);

        return mapper.toVO(fato);
    }

    private Fato pega2(final Integer id) throws EntidadeInexistenteException {

        log.trace("Pegando fato da operação de id {}.", id);

        final Integer idFato = mapper.toKey(id);

        log.trace("Pegando fato de id {}.", id);

        return
            fatoRepo
                .findById(idFato)
                .orElseThrow(
                    () -> new EntidadeInexistenteException(Operacao.class, id)
                );
    }

    @Override
    public Page<Operacao> lista(final Periodo periodo, final Pageable pageable){
    	log.debug("Listando operações por {}.", periodo);

    	return
	        fatoRepo
	            .findByPeriodo(periodo,pageable)
	            .map(mapper::toVOSimples);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public Operacao atualiza(final Integer key, final FatoVO atualizacao)
            throws BusinessException {

        log.debug(
            "Atualizando operação de id {} com {}.", key, atualizacao
        );

        final Fato fato = pega2(key);

        log.trace("Atualizando fato {} com {}.", fato, atualizacao);

        fato.setDia(atualizacao.getDia());
        fato.setDescricao(atualizacao.getDescricao());

    	return mapper.toVO(fato);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer key)
    		throws EntidadeInexistenteException {
    	log.debug("Deletando operação de id {}.", key);

    	final Fato fato = pega2(key);

    	log.trace("Deletando fato {}.", fato);

    	fatoRepo.delete(fato);
    }
}
