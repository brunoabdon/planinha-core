package com.github.brunoabdon.planinha.rest.paramconverters;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.ParamConverter;

public class YearMonthParamConverter implements ParamConverter<YearMonth> {

	public static final YearMonthParamConverter INSTANCE =
		new YearMonthParamConverter();

	private static final DateTimeFormatter MM_YYYY =
		DateTimeFormatter.ofPattern("MM-YYYY");

	@Override
	public YearMonth fromString(final String str) {
		if(str == null) throw new IllegalArgumentException("Valor nulo.");
		return YearMonth.parse(str,MM_YYYY);
	}

	@Override
	public String toString(final YearMonth mes) {
		if(mes == null) throw new IllegalArgumentException("Mes nulo.");

		return mes.format(MM_YYYY);
	}

}
