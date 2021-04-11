package com.github.brunoabdon.planinha.util;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

/**
 * Métodos utilitários nem tão utilitários mas úteis pra deixar os métodos de
 * negócio mais legíveis. E mais testáveis.
 *
 * @author bruno
 *
 */
@Component
public class TimeUtils {

    private static final TemporalAdjuster NO_INICIO_DO_MES = firstDayOfMonth();

    private static final TemporalAdjuster NO_FIM_DO_MES =
        TemporalAdjusters.lastDayOfMonth();

    private static final UnaryOperator<LocalDate> PASSA_PRO_MES_SEGUINTE =
        d -> d.plusMonths(1);

    public LocalDate noInicioDoMes(final LocalDate dia) {
        return dia.with(NO_INICIO_DO_MES);
    }

    public LocalDate noFimDoMes(final LocalDate dia) {
        return dia.with(NO_FIM_DO_MES);
    }

    public long quantosMesesEntre(
            final LocalDate primeiroDia,
            final LocalDate ultimoDia) {
        return Period.between(primeiroDia, ultimoDia).toTotalMonths();
    }

    public Stream<LocalDate> streamMensal(final LocalDate dia){
        final LocalDate diaInicial = noInicioDoMes(dia);

        return Stream.iterate(diaInicial, PASSA_PRO_MES_SEGUINTE);
    }

    public Stream<LocalDate> streamMensal(
            final LocalDate diaInicio,
            final LocalDate diaFinal){

        long quantidadeDeMeses = quantosMesesEntre(diaInicio, diaFinal);

        return streamMensal(diaInicio).limit(quantidadeDeMeses);
    }

    public Stream<LocalDate> streamMensalAteHoje(
            final LocalDate diaInicio){

        final LocalDate hoje = LocalDate.now();

        long quantidadeDeMeses = quantosMesesEntre(diaInicio, hoje);

        return streamMensal(diaInicio).limit(quantidadeDeMeses);
    }
}
