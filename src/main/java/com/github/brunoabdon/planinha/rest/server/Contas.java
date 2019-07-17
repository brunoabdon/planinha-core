package com.github.brunoabdon.planinha.rest.server;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.gastoso.dal.ContasDao;

@Stateless
@Path(Contas.PATH)
public class Contas extends AbstractRestCrud<Conta, Integer> {

	public static final String PATH = "contas";
	
	private Dao<Conta, Integer> dao;
	
	public Contas() {
		super(PATH);
		this.dao = new ContasDao();
	}

	@Override
	protected Dao<Conta, Integer> getDao() {
		return this.dao;
	}

}
