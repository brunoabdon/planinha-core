package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.facade.filtro.FiltroOperacao;
import com.github.brunoabdon.planinha.modelo.Operacao;

@ApplicationScoped
public class OperacaoFacade 
        implements Facade<Operacao, Integer, FiltroOperacao, Void>{

	@Inject 
	Logger logger;

    @Override
    public Operacao cria(final Operacao elemento) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Operacao pega(final Integer key) throws EntidadeInexistenteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Operacao> listar(final FiltroOperacao filtro) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Operacao atualiza(final Integer key, final Void atualizacao) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleta(final Integer key) throws EntidadeInexistenteException, BusinessException {
        // TODO Auto-generated method stub
        
    }
    
    
}
