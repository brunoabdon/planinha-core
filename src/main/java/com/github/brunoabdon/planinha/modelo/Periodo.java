/*
 * Copyright (C) 2016 Bruno Abdon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.brunoabdon.planinha.modelo;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoUnit.DAYS;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Um espaço de tempo limitado por dois {@link LocalDate dias}.
 * @author Bruno Abdon
 */
public class Periodo implements Serializable {

    private static final long serialVersionUID = 5639145559450301864L;

    public static final Pattern EXTRADO_ID_REGEXP =
        Pattern.compile("^(\\d+(-(\\d){2}){2})-(\\d+)");

    private LocalDate inicio;
    private LocalDate fim;

    public Periodo(final LocalDate inicio, final LocalDate fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public static Periodo mesDoDia(final LocalDate dia) {

        final LocalDate diaPrimeiro = dia.withDayOfMonth(1);
        final LocalDate fimDoMes = dia.plusMonths(1).minusDays(1);

        return new Periodo(diaPrimeiro,fimDoMes);
    }

    public static Periodo mes(final YearMonth mes) {
    	final LocalDate dataMinima = mes.atDay(1);
    	final LocalDate dataMaxima = mes.atEndOfMonth();
    	return new Periodo(dataMinima, dataMaxima);
    }

    
    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(final LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(final LocalDate fim) {
        this.fim = fim;
    }

    /**
     * Formata este período como a string {@code YYYY-MM-DD-dd} onde:
     * <ul>
     *   <li><em>YYYY-MM-DD</em> é a {@linkplain Periodo#getInicio() data
     *   inicial do período}, no formato {@link
     *   DateTimeFormatter#ISO_LOCAL_DATE};</li>
     *   <li><em>dd</em> é a quantidade de dias entre a data inicio e a data
     *   fim deste período (inclusivamente).</li>
     * </ul>
     *
     * @return Este periodo formatado como uma string.
     */
    public String serialize() {
        final long quantosDias = DAYS.between(inicio, fim.plusDays(1));
        final String inicioFormatado = inicio.format(ISO_LOCAL_DATE);

        return
            new StringBuilder(16)
                .append(inicioFormatado)
                .append("-")
                .append(quantosDias)
                .toString();
    }

    public static Periodo fromString(final String str) {

        if(str == null) return null;

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

    private static LocalDate parseData(final String strData) {
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

    private static int parseNumero(final String numero, final String desc) {
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


    @Override
    public String toString() {
    	return "["+inicio+"->"+fim+"]";
    }
}
