package com.github.brunoabdon.planinha.facade.mapper;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.modelo.Operacao;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;

@Slf4j
@Component
@Setter(PACKAGE)
public class ItemDeExtratoMapper implements Mapper<Lancamento, ItemDeExtrato> {

    @Autowired
    private Mapper<Fato, Operacao> mapperOperacao;

    @Override
    public ItemDeExtrato toVO(final Lancamento lancamento) {

        log.trace("Mapeando pra VO {}.", lancamento);

        final Fato fato = lancamento.getFato();

        final Operacao operacao = mapperOperacao.toVOSimples(fato);

        return new ItemDeExtrato(operacao, lancamento.getValor());
    }
}
