package com.github.brunoabdon.planinha.rest.modelfiller;

import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import com.github.brunoabdon.planinha.rest.model.OperacaoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OperacaoModelFiller implements ModelFiller<OperacaoModel, Operacao> {

    @Autowired
    private RepresentationModelAssembler<Operacao,FatoModel> fatoAssembler;

    @Override
    public void fillModel(
            final OperacaoModel operacaoModel,
            final Operacao operacaoValue) {

        operacaoModel.setId(operacaoValue.getId());
        final FatoModel fatoModel =
            fatoAssembler.toModel(operacaoValue);

        operacaoModel.setFato(fatoModel);
        //TODO preencher movimentacoes
        //operacaoModel.setMovimentacoes(movimentacoes);


    }
}
