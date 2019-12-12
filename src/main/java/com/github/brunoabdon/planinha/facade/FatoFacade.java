package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.facade.patch.PatchFato;
import com.github.brunoabdon.planinha.modelo.Fato;

@ApplicationScoped
public class FatoFacade
        implements Facade<Fato, Integer, Void, PatchFato> {

    @Override
    public Fato cria(final Fato elemento) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Fato pega(final Integer key) throws EntidadeInexistenteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Fato> listar(final Void filtro) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Fato atualiza(final Integer key, final PatchFato atualizacao)
            throws EntidadeInexistenteException, BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleta(final Integer key) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

}
