package com.github.brunoabdon.planinha.rest.modelfiller;

import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import com.github.brunoabdon.planinha.rest.model.OperacaoModel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;

@Component
@Setter(PACKAGE)
public class OperacaoModelFiller implements ModelFiller<OperacaoModel, Operacao> {

    @Setter
    @Autowired
    private RepresentationModelsAssembler<Operacao,FatoModel> fatoAssembler;

    @Setter
    @Autowired
    private RepresentationModelsAssembler<Movimentacao,MovimentacaoModel>
            movimentacoesAssembler;

    @Override
    public void fillModel(
            final OperacaoModel operacaoModel,
            final Operacao operacaoValue) {

        //id
        final Integer id = operacaoValue.getId();
        operacaoModel.setId(id);

        //fato
        final FatoModel fatoModel = fatoAssembler.toModel(operacaoValue);
        operacaoModel.setFato(fatoModel);

        //movimentacoes
        final CollectionModel<MovimentacaoModel> movimentacaoModel =
            movimentacoesAssembler.toCollectionModel(
                operacaoValue.getMovimentacoes()
            );

        operacaoModel.setMovimentacoes(movimentacaoModel);
    }

}
