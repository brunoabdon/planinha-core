package com.github.brunoabdon.planinha.planinhacore.rest;

import static org.jboss.logging.Logger.Level.INFO;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

@ApplicationScoped
@Path("/hello")
public class HelloWorldEndpoint {

	private EntityManager em;

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

	
	@Inject
	void setLogger(final Logger logger) {
        this.logger = logger;
    }

	@PersistenceContext
	void setEm(final EntityManager em) {
        this.em = em;
    }

}