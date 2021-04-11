package com.github.brunoabdon.planinha.facade.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ItemDeExtratoMapper implements Mapper<Lancamento, ItemDeExtrato> {

    @Autowired
    private Mapper<Fato, FatoVO> mapperFato;

    @Override
    public ItemDeExtrato toVO(final Lancamento lancamento) {

        log.trace("Mapeando pra VO {}.", lancamento);

        final FatoVO fato = mapperFato.toVOSimples(lancamento.getFato());

        return new ItemDeExtrato(fato, lancamento.getValor());
    }
}
