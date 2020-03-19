package com.github.brunoabdon.planinha.rest.paramconverters;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.LocalDate;

import javax.ws.rs.ext.ParamConverter;

public class LocalDateParamConverter implements ParamConverter<LocalDate> {

	public static final LocalDateParamConverter INSTANCE =
		new LocalDateParamConverter();

	@Override
	public LocalDate fromString(final String str) {
		if(str == null) throw new IllegalArgumentException("Valor nulo.");
		return LocalDate.parse(str,ISO_LOCAL_DATE);
	}

	@Override
	public String toString(final LocalDate localDate) {
		if(localDate == null) throw new IllegalArgumentException("Dia nulo.");

		return localDate.format(ISO_LOCAL_DATE);
	}

}
