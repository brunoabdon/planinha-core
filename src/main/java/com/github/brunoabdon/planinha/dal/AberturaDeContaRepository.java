package com.github.brunoabdon.planinha.dal;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.virtual.AberturaDeConta;

@Repository
public interface AberturaDeContaRepository
    extends Repositorio<AberturaDeConta, Integer> {

    Optional<AberturaDeConta> findByConta(final Conta conta);

}
