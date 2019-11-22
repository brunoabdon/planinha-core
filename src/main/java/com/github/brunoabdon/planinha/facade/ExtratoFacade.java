package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;

@ApplicationScoped
public class ExtratoFacade
        implements Facade<Extrato, Extrato.Id, Conta, Void> {

    @Override
    public Extrato cria(final Extrato elemento) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Extrato pega(final Id key) throws EntidadeInexistenteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Extrato> listar(final Conta conta) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Extrato atualiza(final Id key, final Void atualizacao) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleta(Id key) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

}
