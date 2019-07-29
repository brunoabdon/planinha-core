package com.github.brunoabdon.planinha.rest.server;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Provider
public class ObjectMapperContextResolver 
		implements ContextResolver<ObjectMapper> {

	private static final Logger LOGGER = 
		Logger.getLogger(ObjectMapperContextResolver.class);
	
	private final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
    	LOGGER.info("Registrando ObjectMapper.");
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
    	LOGGER.info("Provendo ObjectMapper.");
        return mapper;
    }	

}
