package com.github.brunoabdon.planinha.rest.paramconverters;

import static org.jboss.logging.Logger.Level.DEBUG;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.YearMonth;

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

    @Inject
    ParamConverter<Periodo> periodoConverter;

    @Inject
    ParamConverter<YearMonth> mesConverter;

    @Inject
    ParamConverter<LocalDate> localDateConverter;

	@Override
	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter(
			final Class<T> rawType,
			final Type genericType,
			final Annotation[] annotations) {

		final ParamConverter<T> converter =
			rawType == Periodo.class
				? (ParamConverter<T>)periodoConverter

				: rawType == YearMonth.class
				? (ParamConverter<T>)mesConverter

				: rawType == LocalDate.class
				? (ParamConverter<T>)localDateConverter

				: null;

		logger.logv(DEBUG, "Meu converter pra {0} é {1}.",rawType,converter);

		return converter;
	}

}
