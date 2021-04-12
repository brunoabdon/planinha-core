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

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    /**
     * Diz se este {@link Periodo} tem um um {@linkplain #getInicio() início} e
     * {@linkplain #getFim() fim} determinados - isto é, direntes de {@code
     * null}.
     * @return {@code true} sse não forem {@code null} o {@linkplain
     * #getInicio() início} nem o {@linkplain #getFim() fim}.
     */
    @JsonIgnore
    public boolean isFechado() {
        return inicio != null && fim != null;
    }

    /**
     * Retorna um {@link Periodo} que contem apenas os dias presentes neste
     * período e no período dado.
     *
     * @param periodo Um {@link Periodo}.
     *
     * @return A interceção entre este período e o períoodo dado.
     */
    public Periodo intersecao(@NotNull final Periodo periodo) {

        final LocalDate maiorInicio =
            maiorInicio(this.getInicio(),periodo.getInicio());
        final LocalDate menorFim =
            menorFim(this.getFim(),periodo.getFim());

        return Periodo.of(maiorInicio, menorFim);
    }

    private LocalDate maiorInicio(final LocalDate data1, final LocalDate data2){
        return nullComp(LocalDate::isAfter, data1, data2);
    }

    private LocalDate menorFim(final LocalDate data1, final LocalDate data2) {
        return nullComp(LocalDate::isBefore, data1, data2);
    }

    private LocalDate nullComp(
            final BiPredicate<LocalDate, LocalDate> comp,
            final LocalDate data1,
            final LocalDate data2) {

        boolean data1nula = data1 == null;
        boolean data2nula = data2 == null;

        if(data1nula && data2nula) return null;

        if(data1nula != data2nula) return data1nula ? data2 : data1;

        return comp.test(data1, data2) ? data1 : data2;
    }

    @Override
    public String toString() {
    	return "["+inicio+"->"+fim+"]";
    }
}
