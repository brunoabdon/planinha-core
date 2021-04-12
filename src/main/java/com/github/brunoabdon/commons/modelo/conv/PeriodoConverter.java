package com.github.brunoabdon.commons.modelo.conv;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoUnit.DAYS;
import static lombok.AccessLevel.PRIVATE;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.springframework.core.convert.converter.Converter;

import com.github.brunoabdon.commons.modelo.Periodo;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PeriodoConverter {

    private static final Pattern EXTRADO_ID_REGEXP =
        Pattern.compile("^(\\d+(-(\\d){2}){2})-(\\d+)");

    /**
     * Um {@link Converter} que lê {@linkplain Periodos} {@linkplain
     * Periodo#isFechado() fechados}.
     */
    public enum FromString implements Converter<String, Periodo>{

        INSTANCE {
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
        };
    }

    /**
     * Um {@link Converter} que formata {@linkplain Periodos} {@linkplain
     * Periodo#isFechado() fechados}.
     */
    public enum ToString implements Converter<Periodo, String> {

        INSTANCE {
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
    }
}
