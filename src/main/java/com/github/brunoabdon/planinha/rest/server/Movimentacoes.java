package com.github.brunoabdon.planinha.rest.server;

import static java.lang.Integer.parseInt;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.planinha.Movimentacao;
import com.github.brunoabdon.planinha.dal.MovimentacoesDao;

/**
 *
 * @author bruno
 */
@Path("operacoes/{operacaoId}/movimentacoes")
@Produces(MediaType.APPLICATION_JSON)
public class Movimentacoes 
        extends AbstractRestCrud<Movimentacao, Integer, Integer> {

    private final MovimentacoesDao dao;

    public Movimentacoes() {
        this.dao = new MovimentacoesDao();
    }
    
    @Override
    protected Dao<Movimentacao, Integer> getDao() {
        return this.dao;
    }

    @Override
    protected Integer getFullId(final Integer contaId) {
        final String operacaoIdParam = 
            uriInfo.getPathParameters().getFirst("operacaoId");
        
        final Integer opId = parseInt(operacaoIdParam);
        
        return contaId + opId; //construir chave composta 
    }
}
