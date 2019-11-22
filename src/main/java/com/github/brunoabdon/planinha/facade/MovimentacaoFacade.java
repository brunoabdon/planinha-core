package com.github.brunoabdon.planinha.facade;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.Lancamento.Id;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;

@ApplicationScoped
public class MovimentacaoFacade 
    implements Facade<Movimentacao,Lancamento.Id,Operacao,Integer> {

    @Override
    public Movimentacao cria(final Movimentacao elemento) 
            throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movimentacao pega(final Id key) throws EntidadeInexistenteException{
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Movimentacao> listar(final Operacao filtro) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movimentacao atualiza(
                final Id key, 
                final Integer valor)
            throws EntidadeInexistenteException, BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleta(Id key) throws EntidadeInexistenteException, BusinessException {
        // TODO Auto-generated method stub
        
    }

}
