package com.github.brunoabdon.planinha.rest.server;

import javax.ws.rs.Path;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestReadOnlyResource;
import com.github.brunoabdon.planinha.Extrato;
import com.github.brunoabdon.planinha.Extrato.Id;
import com.github.brunoabdon.planinha.dal.ExtratosDao;

@Path(Extratos.PATH)
public class Extratos extends AbstractRestReadOnlyResource<Extrato, Id> {

    protected static final String PATH = "extratos";

    private ExtratosDao extratosDao;

    public Extratos() {
        this.extratosDao = new ExtratosDao();
    }

    @Override
    protected Dao<Extrato, Id> getDao() {
        return this.extratosDao;
    }
}
