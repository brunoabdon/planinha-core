package com.github.brunoabdon.planinha.modelo.conv;

import com.github.brunoabdon.commons.modelo.Periodo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class PeriodoStringConverter implements Converter<Periodo, String> {

    /**
     * Formata um {@link Periodo} {@linkplain Periodo#isFechado()
     * fechado} como a string {@code YYYY-MM-DD-dd} onde:
     * <ul>
     *   <li><em>YYYY-MM-DD</em> é a {@linkplain Periodo#getInicio()
     *   data inicial do período}, no formato {@link
     *   DateTimeFormatter#ISO_LOCAL_DATE};</li>
     *   <li><em>dd</em> é a quantidade de dias entre a data inicio e a
     *   data fim deste período (inclusivamente).</li>
     * </ul>
     *
     * <p>Caso o período não seja fechado, lança {@link
     * IllegalArgumentException}.</p>
     *
     * @return Este periodo formatado como uma string.
     * @throws IllegalArgumentException caso o {@link Periodo} não seja
     * {@link Periodo#isFechado() fechado}.
     *
     */
    @Override
    public String convert(final Periodo periodo) {

        final LocalDate inicio = periodo.getInicio();
        final LocalDate fim = periodo.getFim();

        final long quantosDias = DAYS.between(inicio, fim.plusDays(1));

        final String inicioFormatado = inicio.format(ISO_LOCAL_DATE);

        return
                new StringBuilder(16)
                        .append(inicioFormatado)
                        .append("-")
                        .append(quantosDias)
                        .toString();
    }
}

