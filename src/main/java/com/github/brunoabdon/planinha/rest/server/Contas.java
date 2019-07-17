package com.github.brunoabdon.planinha.rest.server;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.gastoso.dal.ContasDao;

@Stateless
@Path(Contas.PATH)
public class Contas extends AbstractRestCrud<Conta, Integer> {

	public static final String PATH = "contas";
	
	private ContasDao dao;
	
	public Contas() {
		super(PATH);
		this.dao = new ContasDao();
	}

	@Override
	protected Dao<Conta, Integer> getDao() {
		return this.dao;
	}
	
    @GET
    public Response listar(
            final @Context Request request,
            final @Context HttpHeaders httpHeaders) {
        
        final List<Conta> contas = dao.listar(entityManager);

        return super.buildResponse(request,httpHeaders,contas);
    }


}
