package com.github.brunoabdon.planinha.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.logging.Logger.Level.INFO;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.brunoabdon.planinha.facade.ContaFacade;
import com.github.brunoabdon.planinha.modelo.Conta;

@Path("contas")
public class Contas {

    
    @Inject
    Logger logger;
    
    @Inject
    ContaFacade facade;
    
    
    @GET
    @Produces(APPLICATION_JSON)
    public Response listar() {
        logger.log(INFO, "Listando contas");
        final List<Conta> contas = facade.listar();
        return Response.ok(contas).build();
    }
    
    
}
