package com.github.brunoabdon.planinha.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.logging.Logger.Level.INFO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.planinha.facade.FatoFacade;
import com.github.brunoabdon.planinha.facade.patch.PatchFato;
import com.github.brunoabdon.planinha.modelo.Fato;

@Path("operacoes/{operacao_id}/fato")
@ApplicationScoped
public class Fatos {
    
    @Inject
    Logger logger;
    
    @Inject
    FatoFacade facade;
   
    @GET
    @Produces(APPLICATION_JSON)
    public Response pegar(@PathParam("operacao_id") final Integer idOperacao) 
            throws EntidadeInexistenteException {
        
        logger.logv(INFO, "Pegando fato da operacao {0}.",idOperacao);
        
        final Fato fato = facade.pega(idOperacao);
        
        return Response.ok(fato).build();
    }
  
    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response atualizar(
    		@PathParam("operacao_id") final Integer idOperacao,
    		final PatchFato patchFato) 
				throws EntidadeInexistenteException, BusinessException {
    
        logger.logv(
    		INFO, "Atualizando fato da operacao {0} pra {1}.",
    		idOperacao, patchFato
		);
        
        final Fato fatoAtualizado = facade.atualiza(idOperacao, patchFato);
        
        return Response.ok(fatoAtualizado).build();
    }
}
