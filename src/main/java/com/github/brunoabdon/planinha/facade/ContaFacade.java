package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.dal.ContaConsultaFiltro;
import com.github.brunoabdon.planinha.modelo.Conta;

@ApplicationScoped
public class ContaFacade implements Facade<Conta,Integer,String,String> {

    @PersistenceContext
    EntityManager em;

    @Inject
    ContaConsultaFiltro consultaPorFiltro;

    @Override
    public Conta pega(final Integer id) throws EntidadeInexistenteException{

        final Conta conta = em.find(Conta.class, id);

        if(conta == null)
            throw new EntidadeInexistenteException(Conta.class, id);

        return conta;
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Conta cria(final Conta conta) throws BusinessException{
        em.persist(conta);
        return conta;
    }

    @Override
    public List<Conta> listar(final String parteDoNome){
        return this.consultaPorFiltro.listar(parteDoNome);
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Conta atualiza(
            final Integer id,
            final String nome)
        throws EntidadeInexistenteException, BusinessException{

        final Conta conta = pega(id);
        conta.setNome(nome);

        return conta;
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer id)
        throws EntidadeInexistenteException, BusinessException{

        final Conta conta = pega(id);

        final boolean estaEmUso = this.consultaPorFiltro.estaEmUso(conta);

        //TODO dar a real
        if(estaEmUso) throw new BusinessException();

        em.remove(conta);
    }
}
