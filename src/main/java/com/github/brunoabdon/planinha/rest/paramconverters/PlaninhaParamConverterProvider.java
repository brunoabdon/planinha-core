package com.github.brunoabdon.planinha.rest.paramconverters;

import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.INFO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.github.brunoabdon.planinha.Extrato;

@Provider
public class PlaninhaParamConverterProvider implements ParamConverterProvider {
	
	private static Logger LOGGER = 
		Logger.getLogger(PlaninhaParamConverterProvider.class);
	
	public PlaninhaParamConverterProvider() {
		LOGGER.logv(INFO, "Criando Provider {0}.", this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter(
			final Class<T> rawType, 
			final Type genericType, 
			final Annotation[] annotations) {
		
		final ParamConverter<T> converter = 
			rawType == Extrato.Id.class 
				? (ParamConverter<T>)ExtratoIdParamConverter.INSTANCE
				: null;
		
		LOGGER.logv(DEBUG, "Meu converter pra {0} é {1}.",rawType,converter);

		return converter;
	}

}
