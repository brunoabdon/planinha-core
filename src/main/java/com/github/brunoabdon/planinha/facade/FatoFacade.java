package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.facade.patch.PatchFato;
import com.github.brunoabdon.planinha.modelo.Fato;

@ApplicationScoped
public class FatoFacade
        implements Facade<Fato, Integer, Void, PatchFato> {

    @PersistenceContext
    EntityManager em;
    
    @Override
    public Fato cria(final Fato elemento) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Fato pega(final Integer key) throws EntidadeInexistenteException {
        final Fato fato = em.find(Fato.class, key);
        
        if(fato == null) 
            throw new EntidadeInexistenteException(Fato.class, key);
        
        return fato;
    }

    @Override
    public List<Fato> listar(final Void filtro) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Fato atualiza(final Integer key, final PatchFato atualizacao)
            throws EntidadeInexistenteException, BusinessException {

        final Fato fato = pega(key);
        fato.setDia(atualizacao.getDia());
        fato.setDescricao(atualizacao.getDescricao());
        
        return fato;
    }

    @Override
    public void deleta(final Integer key) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

}
