package com.github.brunoabdon.planinha.facade;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.WARN;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Movimentacao.Id;
import com.github.brunoabdon.planinha.modelo.Operacao;

@ApplicationScoped
public class MovimentacaoFacade
        implements Facade<Movimentacao, Id, Integer, Integer> {

    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    OperacaoFacade operacaoFacade;

    @Inject
    IdentifiableMapper<Lancamento,Lancamento.Id,Movimentacao,Movimentacao.Id>
        mapper;

    @Inject
    IdentifiableMapper<Fato, Integer, Operacao, Integer> mapperOperacao;

    @Inject
    IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapperConta;

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Movimentacao cria(@NotNull @Valid final Movimentacao movimentacao)
            throws BusinessException, EntidadeInexistenteException {

        logger.logv(DEBUG, "Criando movimentação {0}.", movimentacao);

        final Fato fato = extraiFato(movimentacao);

        final Conta conta = extraiConta(movimentacao);

        final boolean fatoTemLancamentoDaConta =
            fato.getLancamentos()
                .stream()
                .map(Lancamento::getConta)
                .filter(conta::equals)
                .findAny()
                .isPresent();

        if(fatoTemLancamentoDaConta) {
            //TODO especificar o erro.. EntidadeInvalidaException, etc..
            logger.logv(
                WARN,
                "Não adicionando mais um lancamento da conta {0} no fato {1}.",
                conta, fato
            );
            throw new BusinessException();
        }

        final Lancamento lancamento =
            new Lancamento(fato, conta, movimentacao.getValor());

        em.persist(lancamento);

        //TODO testar se é necessário mesmo....
        final Lancamento lancamentoCriado =
            em.find(Lancamento.class, lancamento.getId());

        return mapper.toVO(lancamentoCriado);
    }

    private Conta extraiConta(final Movimentacao movimentacao)
            throws BusinessException {

        final Integer idConta = movimentacao.getConta().getId();

        final Conta conta = em.find(Conta.class, idConta);
        if(conta == null) {
           //TODO especializar. EntidadeInvalidaExceptio? ContaInvalida...
            throw new BusinessException();
        }
        return conta;
    }

    private Fato extraiFato(final Movimentacao movimentacao)
            throws IdMappingException, EntidadeInexistenteException {

        final Integer idOperacao = movimentacao.getId().getOperacaoId();

        final Integer idFato = mapperOperacao.toKey(idOperacao);

        final Fato fato = em.find(Fato.class, idFato);

        if(fato == null) {
            logger.logv(
                WARN, "Não existe um fato {0} como pede {1}.",
                idFato, movimentacao
            );
            throw new EntidadeInexistenteException(Operacao.class, idOperacao);
        }
        return fato;
    }

    @Override
    public Movimentacao pega(@NotNull @Valid final Movimentacao.Id id)
            throws EntidadeInexistenteException {

        final Lancamento lancamento = pega_(id);

        return mapper.toVO(lancamento);
    }

    private Lancamento pega_(final Movimentacao.Id id)
            throws IdMappingException, EntidadeInexistenteException {

        final Lancamento.Id idLancamento = mapper.toKey(id);

        final Lancamento lancamento = em.find(Lancamento.class, idLancamento);

        if(lancamento == null) {
            logger.logv(
                WARN, "Não existe um lancamento {0} como pede {1}.",
                idLancamento, id
                );
            throw new EntidadeInexistenteException(Movimentacao.class, id);

        }
        return lancamento;
    }

    @Override
    public List<Movimentacao> lista(final Integer idOperacao)
            throws EntidadeInexistenteException {
        return operacaoFacade.pega(idOperacao).getMovimentacoes();
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Movimentacao atualiza(
                @NotNull @Valid final Id id,
                @NotNull @Valid final Integer valor)
            throws EntidadeInexistenteException, BusinessException {

        final Lancamento lancamento = this.pegaLancamento(id);
        lancamento.setValor(valor);
        return mapper.toVO(lancamento);
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(@NotNull @Valid final Movimentacao.Id id)
            throws EntidadeInexistenteException, BusinessException {

        final Lancamento lancamento = pegaLancamento(id);

        final List<Lancamento> lancamentos =
            lancamento.getFato().getLancamentos();

        lancamentos.remove(lancamento);

        if(lancamentos.size() == 0) {
            logger.logv(
                WARN, "Impedindo a remoção de lancamento filho único {0}.",
                lancamento
            );
            //TODO dar a real que a operação não pode ficar sem movimentacoes
            throw new BusinessException();
        }
    }

    private Lancamento pegaLancamento(final Movimentacao.Id id)
            throws IdMappingException {
        final Lancamento.Id k = mapper.toKey(id);

        final Lancamento lancamento = em.find(Lancamento.class, k);

        if(lancamento == null) {
            new EntidadeInexistenteException(Movimentacao.class, id);
        }
        return lancamento;
    }

}
