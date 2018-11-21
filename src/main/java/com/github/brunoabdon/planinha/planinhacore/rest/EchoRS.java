package com.github.brunoabdon.planinha.planinhacore.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

@Path("echo/{what}")
public class EchoRS {

	private static final Logger logger = Logger.getLogger(EchoRS.class);
	
	@GET
	public String echo(@PathParam("what") final String what) {
		
		logger.logv(Level.DEBUG, "Msg debug: {0}.", what);
		logger.logv(Level.INFO, "Msg info: {0}.", what);
		logger.logv(Level.WARN, "Msg warn: {0}.", what);
		logger.logv(Level.ERROR, "Msg error: {0}.", what);
		return "Hello " + what;
	}
}
