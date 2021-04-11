package com.github.brunoabdon.planinha.dal;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento.Id;
import com.github.brunoabdon.planinha.modelo.Periodo;

@Repository
public interface LancamentoRepository extends Repositorio<Lancamento, Id> {

    public boolean existsByConta(final Conta conta);

    public default List<Lancamento> findByContaPeriodo(
            final Conta conta, final Periodo periodo) {
        return
            this.findByContaAndFatoDiaBetween(
                conta,
                periodo.getInicio(),
                periodo.getFim()
            );
    }

    public List<Lancamento> findByContaAndFatoDiaBetween(
            final Conta conta,
            final LocalDate dataMinima,
            final LocalDate dataMaxima);


    public long countByConta(final Conta conta);
}
