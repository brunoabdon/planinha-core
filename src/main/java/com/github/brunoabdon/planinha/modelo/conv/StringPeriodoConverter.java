package com.github.brunoabdon.planinha.modelo.conv;

import com.github.brunoabdon.commons.modelo.Periodo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Component
public class StringPeriodoConverter implements Converter<String, Periodo> {

    private static final Pattern EXTRADO_ID_REGEXP =
        Pattern.compile("^(\\d+(-(\\d){2}){2})-(\\d+)");

    /**
     * Lê um {@link Periodo} de uma string como {@code YYYY-MM-DD-dd}
     * onde:
     * <ul>
     *   <li><em>YYYY-MM-DD</em> é a {@linkplain Periodo#getInicio()
     *   data inicial do período}, no formato {@link
     *   DateTimeFormatter#ISO_LOCAL_DATE};</li>
     *   <li><em>dd</em> é a quantidade de dias entre a data inicio e a
     *   data fim deste período (inclusivamente).</li>
     * </ul>
     *
     * <p>Como consequencia do formato exigido, o {@link Periodo}
     * retornado sempre será {@linkplain Periodo#isFechado() fechado}.
     * </p>
     *
     * @return Este periodo formatado como uma string.
     * @throws IllegalArgumentException Caso a string não obedeça o
     * formato.
     */
    @Override
    public Periodo convert(final String str) {

        final Matcher matcher = EXTRADO_ID_REGEXP.matcher(str);

        if(!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Não segue " + EXTRADO_ID_REGEXP + ": \"" + str + "\"."
            );
        }

        final String strDataInicio = matcher.group(1);

        final LocalDate inicio = parseData(strDataInicio);

        final String strQuantosDias = matcher.group(4);
        final int quantosDias =
                parseNumero(strQuantosDias, "uma quantidade de dias");

        final LocalDate fim = inicio.plusDays(quantosDias);

        return new Periodo(inicio,fim);
    }

    private LocalDate parseData(@NotNull final String strData) {

        Objects.requireNonNull(strData);

        final LocalDate inicio;

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
