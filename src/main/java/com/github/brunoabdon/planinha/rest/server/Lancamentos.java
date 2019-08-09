package com.github.brunoabdon.planinha.rest.server;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.gastoso.dal.LancamentosDao;

/**
 *
 * @author bruno
 */
@Path("lancamentos")
@Produces(MediaType.APPLICATION_JSON)
public class Lancamentos extends AbstractRestCrud<Lancamento, Integer> {

    private final LancamentosDao dao;

    public Lancamentos() {
        this.dao = new LancamentosDao();
    }

    @Override
    protected LancamentosDao getDao() {
        return dao;
    }
    
    
}