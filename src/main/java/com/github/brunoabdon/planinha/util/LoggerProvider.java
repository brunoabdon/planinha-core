package com.github.brunoabdon.planinha.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

@ApplicationScoped
public class LoggerProvider {

	@Produces
	public Logger createLogger(final InjectionPoint injectionPoint) {
		return 
			Logger.getLogger(
				injectionPoint.getMember().getDeclaringClass().getName()
			);
	}
}
