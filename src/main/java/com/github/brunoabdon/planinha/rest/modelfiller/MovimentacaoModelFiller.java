package com.github.brunoabdon.planinha.rest.modelfiller;

import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoModelFiller implements ModelFiller<MovimentacaoModel, Movimentacao> {

    @Setter
    @Autowired
    private RepresentationModelAssembler<ContaVO, ContaModel> contaAssembler;

    @Override
    public void fillModel(
        final MovimentacaoModel model,
        final Movimentacao value) {

        final ContaVO conta = value.getConta();
        final ContaModel contaModel = contaAssembler.toModel(conta);

        model.setConta(contaModel);
        model.setValor(value.getValor());

    }
}
