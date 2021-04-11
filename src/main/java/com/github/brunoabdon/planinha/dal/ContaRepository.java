package com.github.brunoabdon.planinha.dal;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.planinha.dal.modelo.Conta;

@Repository
public interface ContaRepository extends Repositorio<Conta, Integer> {

    public Page<Conta> findByNomeContainingIgnoreCase(
        final String parteDoNome, final Pageable pageable);

    public Stream<Conta> findByNomeContainingIgnoreCase(final String parteDoNome);

    public long countByIdIn(final Iterable<Integer> ids);

}
