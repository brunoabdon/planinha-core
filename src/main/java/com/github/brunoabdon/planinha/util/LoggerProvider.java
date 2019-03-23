package com.github.brunoabdon.planinha.util;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

@ManagedBean
@ApplicationScoped
class LoggerProvider {

	@Produces
	public Logger createLogger(final InjectionPoint injectionPoint) {
		return 
			Logger.getLogger(
				injectionPoint.getMember().getDeclaringClass().getName()
			);
	}
}
