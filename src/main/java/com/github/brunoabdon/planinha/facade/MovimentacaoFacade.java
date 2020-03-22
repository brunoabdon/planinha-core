package com.github.brunoabdon.planinha.facade;

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
import com.github.brunoabdon.planinha.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.Lancamento.Id;
import com.github.brunoabdon.planinha.modelo.Operacao;

@ApplicationScoped
public class MovimentacaoFacade
        implements Facade<Lancamento, Id, Integer, Integer> {

    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    OperacaoFacade operacaoFacade;

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Lancamento cria(@NotNull @Valid final Lancamento movimentacao)
            throws BusinessException {
        //TODO validar se conta não já tem na operação
        em.persist(movimentacao);
        return movimentacao;
    }

    @Override
    public Lancamento pega(@NotNull @Valid final Id id)
            throws EntidadeInexistenteException {

        final Lancamento movimentacao = em.find(Lancamento.class, id);

        if(movimentacao == null)
            throw new EntidadeInexistenteException(Lancamento.class, id);

        return movimentacao;
    }

    @Override
    public List<Lancamento> lista(final Integer idOperacao)
            throws EntidadeInexistenteException {
        return operacaoFacade.pega(idOperacao).getMovimentacoes();
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Lancamento atualiza(
                @NotNull @Valid final Id id,
                @NotNull @Valid final Integer valor)
            throws EntidadeInexistenteException, BusinessException {

        final Lancamento movimentacao = this.pega(id);
        movimentacao.setValor(valor);
        return movimentacao;
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(@NotNull @Valid final Id id)
            throws EntidadeInexistenteException, BusinessException {

        final Operacao operacao = operacaoFacade.pega(id.getOperacaoId());

        final List<Lancamento> movimentacoes = operacao.getMovimentacoes();

        final Lancamento movimentacao =
            movimentacoes
            .stream()
            .filter(m -> m.getId().equals(id))
            .findAny()
            .orElseThrow(
                () -> new EntidadeInexistenteException(Lancamento.class, id)
            );

        if(movimentacoes.size() == 1) {
            //TODO dar a real que a operação não pode ficar sem movimentacoes
            throw new BusinessException();
        }

        em.remove(movimentacao);
    }

}
