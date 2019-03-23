package com.github.brunoabdon.planinha.planinhacore.rest;

import static org.jboss.logging.Logger.Level.INFO;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.persistence.*;

@ApplicationScoped
@Path("/hello")
public class HelloWorldEndpoint {

    @PersistenceContext(unitName="planinhaPU")
    private EntityManager em;

    @Inject
    private Logger logger;
    
	@GET
	@Produces("text/plain")
	public Response doGet() {

		final Query q = em.createNativeQuery("SELECT id, nome FROM Conta c");

		@SuppressWarnings("unchecked")
		final List<Object[]> contas = q.getResultList();
		 
		for (final Object[] c : contas) {
			logger.logv(INFO, "Conta {0} {1}",c[0],c[1]);
		}

		return Response.ok("Veja o log").build();
	}
}