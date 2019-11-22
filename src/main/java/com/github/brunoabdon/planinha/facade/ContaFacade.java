package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.facade.filtro.FiltroConta;
import com.github.brunoabdon.planinha.modelo.Conta;

@ApplicationScoped
public class ContaFacade implements Facade<Conta,Integer,FiltroConta,String> {

    @Override
    public Conta pega(final Integer id) throws EntidadeInexistenteException{
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Conta cria(final Conta conta) throws BusinessException{
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Conta> listar(final FiltroConta filtro){
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Conta atualiza(final Integer id, final String nome) 
        throws EntidadeInexistenteException, BusinessException{
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer id) 
        throws EntidadeInexistenteException, BusinessException{
        // TODO Auto-generated method stub
    }
}
