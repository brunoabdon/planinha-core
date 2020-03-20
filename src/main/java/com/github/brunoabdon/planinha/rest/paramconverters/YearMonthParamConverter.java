package com.github.brunoabdon.planinha.rest.paramconverters;

import java.time.YearMonth;

import javax.ws.rs.ext.ParamConverter;

public class YearMonthParamConverter implements ParamConverter<YearMonth> {

	public static final YearMonthParamConverter INSTANCE =
		new YearMonthParamConverter();

	@Override
	public YearMonth fromString(final String str) {
		if(str == null) throw new IllegalArgumentException("Valor nulo.");
		return YearMonth.parse(str);
	}

	@Override
	public String toString(final YearMonth mes) {
		if(mes == null) throw new IllegalArgumentException("Mes nulo.");
		return mes.toString();
	}

}
