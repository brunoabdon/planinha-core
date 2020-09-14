package com.github.brunoabdon.planinha.rest.tools.paramconverters;

import java.time.YearMonth;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ParamConverter;

@ApplicationScoped
public class YearMonthParamConverter implements ParamConverter<YearMonth> {

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
