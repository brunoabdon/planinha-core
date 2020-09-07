package com.github.brunoabdon.planinha.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.logging.Logger.Level.INFO;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.facade.ContaFacade;
import com.github.brunoabdon.planinha.facade.ExtratoFacade;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.modelo.Periodo;

@ApplicationScoped
@Path("contas/{conta_id}/extratos")
public class Extratos {

    @Inject
    Logger logger;

    @Inject
    ContaFacade contaFacade;

    @Inject
    ExtratoFacade facade;
    
    @GET
    @Produces(APPLICATION_JSON)
    public Response listar(
            @PathParam("conta_id") final Integer idConta) 
                throws EntidadeInexistenteException {

        logger.logv(INFO, "Listando extratos da conta {0}.", idConta);

        final List<Extrato> extratosDaConta = facade.lista(idConta);
        if(extratosDaConta.isEmpty()) {
            this.contaFacade.pega(idConta); //excecao se nao existir a conta
        }

        return Response.ok(extratosDaConta).build();
    }
    
    @GET
    @Path("{periodo}")
    @Produces(APPLICATION_JSON)
    public Response pegar(
            @PathParam("conta_id") final Integer idConta,
            @PathParam("periodo") final Periodo periodo) 
                throws EntidadeInexistenteException {
        
        final Extrato.Id id = new Id(new Conta(idConta), periodo);

        final Extrato extrato = facade.pega(id);
        
        return Response.ok(extrato).build();
    }
    
}
