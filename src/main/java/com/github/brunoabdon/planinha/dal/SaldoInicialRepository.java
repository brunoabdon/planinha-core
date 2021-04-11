package com.github.brunoabdon.planinha.dal;

import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.planinha.dal.modelo.virtual.SaldoInicial;
import com.github.brunoabdon.planinha.dal.modelo.virtual.SaldoInicial.Id;

@Repository
public interface SaldoInicialRepository extends Repositorio<SaldoInicial, Id> {

}
