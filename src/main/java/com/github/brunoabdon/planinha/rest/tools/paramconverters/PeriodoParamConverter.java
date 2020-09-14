package com.github.brunoabdon.planinha.rest.tools.paramconverters;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.ParamConverter;

import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.modelo.Periodo;


@ApplicationScoped
public class PeriodoParamConverter implements ParamConverter<Periodo>{

    public static final Pattern EXTRADO_ID_REGEXP =
        Pattern.compile("^(\\d+(-(\\d){2}){2})-(\\d+)");

    /**
     * Formata um {@link Periodo} como a string {@code YYYY-MM-DD-dd} onde:
     * <ul>
     *   <li><em>YYYY-MM-DD</em> é a {@linkplain Id#getPeriodo() data inicial
     *   do id}, no formato {@link DateTimeFormatter#ISO_LOCAL_DATE};</li>
     *   <li><em>dd</em> é a quantidade de dias entre a data inicio e a data
     *   fim do Id (inclusivamente).</li>
     * </ul>
     *
     * @param periodo .
     *
     * @return O id formatado como uma string.
     */
	@Override
	public String toString(final Periodo periodo) {
        return periodo.serialize();
    }

	@Override
	public Periodo fromString(final String str) {
	    if(str == null) throw new IllegalArgumentException();
	    return Periodo.fromString(str);
    }
}
