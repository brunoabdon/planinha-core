package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import com.github.brunoabdon.planinha.rest.model.ItemDeExtratoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemDeExtratoAssembler implements RepresentationModelsAssembler<ItemDeExtrato, ItemDeExtratoModel> {

    @Autowired
    private RepresentationModelsAssembler<Operacao, FatoModel> fatoAssembler;

    @Override
    public ItemDeExtratoModel toModel(final ItemDeExtrato itemDeExtrato) {

        final Operacao operacao = itemDeExtrato.getOperacao();

        final FatoModel fatoModel = fatoAssembler.toModel(operacao);

        final int valor = itemDeExtrato.getValor();

        return new ItemDeExtratoModel(fatoModel, valor);
    }
}