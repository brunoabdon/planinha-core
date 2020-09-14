package com.github.brunoabdon.planinha.facade.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;

@ApplicationScoped
public class ItemDeExtratoMapper implements Mapper<Lancamento, ItemDeExtrato> {

    @Inject
    Mapper<Fato, FatoVO> mapperFato;

    @Override
    public ItemDeExtrato toVO(final Lancamento lancamento) {

        final FatoVO fato = mapperFato.toVOSimples(lancamento.getFato());

        return new ItemDeExtrato(fato, lancamento.getValor());
    }
}
