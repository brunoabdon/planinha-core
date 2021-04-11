package com.github.brunoabdon.planinha.dal;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.modelo.Periodo;

@Repository
public interface FatoRepository extends Repositorio<Fato, Integer> {


    public default Stream<Fato> findByPeriodo(
            final Periodo periodo) {
        return
            this.findByDiaBetween(
                periodo.getInicio(),
                periodo.getFim()
            );
    }

    public Stream<Fato> findByDiaBetween(
            final LocalDate dataMinima,
            final LocalDate dataMaxima);
}
