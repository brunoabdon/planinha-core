package com.github.brunoabdon.planinha.rest.server;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestReadOnlyResource;
import com.github.brunoabdon.planinha.Extrato;
import com.github.brunoabdon.planinha.Extrato.Id;
import com.github.brunoabdon.planinha.dal.ExtratosDao;

@Path("extratos")
@Produces(MediaType.APPLICATION_JSON)
public class Extratos extends AbstractRestReadOnlyResource<Extrato, Id, Id> {

    @Inject
    private ExtratosDao extratosDao;

    @Override
    protected Dao<Extrato, Id> getDao() {
        return this.extratosDao;
    }

    @Override
    protected Id getFullId(final Id pathId) {
        return pathId;
    }
}
