package com.github.brunoabdon.planinha.rest.paramconverters;

import static org.jboss.logging.Logger.Level.DEBUG;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.github.brunoabdon.planinha.modelo.Periodo;

@Provider
public class PlaninhaParamConverterProvider implements ParamConverterProvider {
	
    @Inject
	Logger logger;
	
	public PlaninhaParamConverterProvider() {
//		logger.logv(INFO, "Criando Provider {0}.", this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter(
			final Class<T> rawType, 
			final Type genericType, 
			final Annotation[] annotations) {
		
		final ParamConverter<T> converter = 
			rawType == Periodo.class 
				? (ParamConverter<T>)PeriodoParamConverter.INSTANCE
				: null;
		
		logger.logv(DEBUG, "Meu converter pra {0} é {1}.",rawType,converter);

		return converter;
	}

}
