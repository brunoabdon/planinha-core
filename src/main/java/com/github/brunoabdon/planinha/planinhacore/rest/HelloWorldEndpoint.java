package com.github.brunoabdon.planinha.planinhacore.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.persistence.*;

@ApplicationScoped
@Path("/hello")
public class HelloWorldEndpoint {

    @PersistenceContext(unitName="planinhaPU")
    private EntityManager em;

	@GET
	@Produces("text/plain")
	public Response doGet() {

	Query q = em.createNativeQuery("SELECT id, nome FROM Conta c");
	java.util.List<Object[]> contas = q.getResultList();
	 
	for (Object[] c : contas) {
		System.out.println("Conta "
		        + c[0]
		        + " "
		        + c[1]);
	}

		return Response.ok("Veja o log").build();
	}
}
