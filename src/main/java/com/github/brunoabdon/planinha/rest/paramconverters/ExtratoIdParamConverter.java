package com.github.brunoabdon.planinha.rest.paramconverters;

import static java.time.LocalDate.parse;
import static java.time.Period.between;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.ext.ParamConverter;

import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.modelo.Periodo;

public class ExtratoIdParamConverter implements ParamConverter<Periodo>{

	public static final ExtratoIdParamConverter INSTANCE = 
		new ExtratoIdParamConverter();

    public static final Pattern EXTRADO_ID_REGEXP =
        Pattern.compile("^(\\d+(-(\\d){2}){2})-(\\d+)");
    
    /**
     * Formata um {@link Id} como a string {@code ccc-YYYY-MM-DD-dd} onde:
     * <ul>
     *   <li><em>ccc</em> É o id da conta (uma sequencia de N digitos);</li>
     *   <li><em>YYYY-MM-DD</em> é a {@linkplain Id#getPeriodo() data inicial
     *   do id}, no formato {@link DateTimeFormatter#ISO_LOCAL_DATE};</li> 
     *   <li><em>dd</em> é a quantidade de dias entre a data inicio e a data
     *   fim do Id (inclusivamente).;</li> 
     * </ul>
     * 
     * @param id O id a ser convertido.
     * 
     * @return O id formatado como uma string.
     */
	@Override
	public String toString(final Periodo periodo) {
        
        final LocalDate inicio = periodo.getDataMinima();
        final LocalDate fim = periodo.getDataMaxima();
        
        final int quantosDias = between(inicio, fim.plusDays(1)).getDays();
        final String inicioFormatado = inicio.format(ISO_LOCAL_DATE);
        
        return
            new StringBuilder(16)
                .append(inicioFormatado)
                .append("-")
                .append(quantosDias)
                .toString();
    }

	@Override
	public Periodo fromString(final String str) {
        final Matcher matcher = EXTRADO_ID_REGEXP.matcher(str);
        
        if(!matcher.matches()) {
            throw new IllegalArgumentException(
                "Não segue " + EXTRADO_ID_REGEXP + ": \"" + str + "\"."
            );
        }
        
        return getPeriodo(matcher);
    }

    private Periodo getPeriodo(final Matcher matcher) {
        final String strDataInicio = matcher.group(1);
        
        final LocalDate inicio = parseData(strDataInicio);
        
        final String strQuantosDias = matcher.group(4);
        final int quantosDias = 
            parseNumero(strQuantosDias, "uma quantidade de dias");
        
        final LocalDate fim = inicio.plusDays(quantosDias);

        return new Periodo(inicio,fim);
    }

    private LocalDate parseData(final String strData) {
        LocalDate inicio;
        
        try {
            inicio = parse(strData, ISO_LOCAL_DATE);
        } catch (final DateTimeException e) {
            throw new IllegalArgumentException(
                "\"" + strData + "\" não é " + ISO_LOCAL_DATE + ".", e
            );
        }
        return inicio;
    }

    private int parseNumero(final String numero, final String desc) {
        final int contaId;
        try {
            contaId = Integer.parseInt(numero);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(
                "\""+numero+"\" não parece " + desc + " válido.", e
            );
        }
        return contaId;
    }

}
