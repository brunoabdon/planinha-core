package com.github.brunoabdon.planinha.rest.server;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.planinha.Operacao;
import com.github.brunoabdon.planinha.OperacaoDao;

@Path(Operacoes.PATH)
public class Operacoes extends AbstractRestCrud<Operacao, Integer>{

    private static final ResponseBuilder NOT_ALLOWED = 
        Response.status(Status.METHOD_NOT_ALLOWED);

    protected static final String PATH = "operacoes";

	private final OperacaoDao operacoesDao;
    
    public Operacoes() {
        super(PATH);
    	this.operacoesDao = new OperacaoDao();
	}

    @Override
    protected Dao<Operacao, Integer> getDao() {
        return this.operacoesDao;
    }

    @Override
    @SuppressWarnings("unused")
    public Response atualizar(final Integer id, final Operacao entity) {
        return NOT_ALLOWED.build();
    }
}
