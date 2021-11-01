package com.github.brunoabdon.planinha.dal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.planinha.dal.modelo.Conta;

@Repository
public interface ContaRepository extends Repositorio<Conta, Integer> {

    Page<Conta> findByNomeContainingIgnoreCase(
        final String parteDoNome, final Pageable pageable);

    long countByIdIn(final Iterable<Integer> ids);

}
