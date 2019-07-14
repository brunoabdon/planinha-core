package com.github.brunoabdon.planinha.rest.server;

import javax.ws.rs.Path;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.gastoso.Conta;

@Path(Contas.PATH)
public class Contas extends AbstractRestCrud<Conta, Integer> {

	public static final String PATH = "contas";
	
	public Contas() {
		super(PATH);
	}

	@Override
	protected Dao<Conta, Integer> getDao() {
		return null; // gastoso dao tá em gastoso-srv...
	}

}
