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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;
import java.util.function.BiPredicate;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Um espaço de tempo limitado por dois {@link LocalDate dias}.
 * @author Bruno Abdon
 */
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Periodo implements Serializable {

    private static final long serialVersionUID = 5639145559450301864L;

    public static final Periodo SEMPRE = new Periodo(null, null);

    private final LocalDate inicio;

    private final LocalDate fim;

    public static Periodo of(final LocalDate inicio, final LocalDate fim) {
        if( inicio == null && fim == null) return SEMPRE;

        return new Periodo(inicio, fim);
    }

    public static Periodo mesDoDia(@NotNull final LocalDate dia) {

        Objects.requireNonNull(dia);

        final LocalDate diaPrimeiro = dia.withDayOfMonth(1);
        final LocalDate fimDoMes = dia.plusMonths(1).minusDays(1);

        return new Periodo(diaPrimeiro,fimDoMes);
    }

    public static Periodo mes(@NotNull final YearMonth mes) {

        Objects.requireNonNull(mes);

    	final LocalDate dataMinima = mes.atDay(1);
    	final LocalDate dataMaxima = mes.atEndOfMonth();
    	return new Periodo(dataMinima, dataMaxima);
    }

    public boolean isFechado() {
        return inicio != null && fim != null;
    }

    public Periodo intersecao(@NotNull final Periodo periodo) {

        final LocalDate maiorInicio = max(this.getInicio(),periodo.getInicio());
        final LocalDate menorFim = min(this.getFim(),periodo.getFim());

        return Periodo.of(maiorInicio, menorFim);
    }

    private LocalDate max(final LocalDate data1, final LocalDate data2) {
        return nullComp(LocalDate::isAfter, data1, data2);
    }

    private LocalDate min(final LocalDate data1, final LocalDate data2) {
        return nullComp(LocalDate::isBefore, data1, data2);
    }
    private LocalDate nullComp(
            final BiPredicate<LocalDate, LocalDate> comp,
            final LocalDate data1,
            final LocalDate data2) {

        if(data1 == null || data2 == null) return null;

        return comp.test(data1, data2) ? data1 : data2;
    }


    @Override
    public String toString() {
    	return "["+inicio+"->"+fim+"]";
    }
}
