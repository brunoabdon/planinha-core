package com.github.brunoabdon.planinha.rest.server;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

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
        this.mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
    	LOGGER.info("Provendo ObjectMapper.");
        return mapper;
    }	

}
