package com.github.brunoabdon.planinha.rest.server;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRootResource;
import com.github.brunoabdon.planinha.Operacao;
import com.github.brunoabdon.planinha.dal.OperacoesDao;

@Path("operacoes")
@Produces(MediaType.APPLICATION_JSON)
public class Operacoes extends AbstractRootResource<Operacao, Integer>{

    private static final ResponseBuilder NOT_ALLOWED = 
        Response.status(Status.METHOD_NOT_ALLOWED);

	@Inject
    private OperacoesDao operacoesDao;
    
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
