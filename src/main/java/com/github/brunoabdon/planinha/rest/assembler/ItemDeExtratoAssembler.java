package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import com.github.brunoabdon.planinha.rest.model.ItemDeExtratoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ItemDeExtratoAssembler implements RepresentationModelAssembler<ItemDeExtrato, ItemDeExtratoModel> {

    @Autowired
    private RepresentationModelAssembler<Operacao, FatoModel> fatoAssembler;

    @Override
    public ItemDeExtratoModel toModel(final ItemDeExtrato itemDeExtrato) {

        final Operacao operacao = itemDeExtrato.getOperacao();

        final FatoModel fatoModel = fatoAssembler.toModel(operacao);

        final int valor = itemDeExtrato.getValor();

        return new ItemDeExtratoModel(fatoModel, valor);
    }
}