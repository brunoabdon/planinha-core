package com.github.brunoabdon.planinha.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.logging.Logger.Level.INFO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.planinha.facade.OperacaoFacade;
import com.github.brunoabdon.planinha.modelo.Operacao;

@Path("operacoes")
@ApplicationScoped
public class Operacoes {
    
    @Inject
    Logger logger;
    
    @Inject
    OperacaoFacade facade;
    
    @GET
    @Path("{operacao_id}")
    @Produces(APPLICATION_JSON)
    public Response pegar(@PathParam("operacao_id") final Integer idOperacao)  
            throws EntidadeInexistenteException {
        
        logger.logv(INFO, "Pegando operacao {0}.",idOperacao);
        
        final Operacao operacao = facade.pega(idOperacao);

        return Response.ok(operacao).build();
    }
}