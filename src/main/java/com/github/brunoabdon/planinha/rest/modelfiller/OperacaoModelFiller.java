package com.github.brunoabdon.planinha.rest.modelfiller;

import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.Movimentacoes;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import com.github.brunoabdon.planinha.rest.model.OperacaoModel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OperacaoModelFiller implements ModelFiller<OperacaoModel, Operacao> {

    @Setter
    @Autowired
    private RepresentationModelAssembler<Operacao,FatoModel> fatoAssembler;

    @Setter
    @Autowired
    private RepresentationModelAssembler<Movimentacao,MovimentacaoModel>
            movimentacoesAssembler;

    @Override
    public void fillModel(
            final OperacaoModel operacaoModel,
            final Operacao operacaoValue) {

        //id
        operacaoModel.setId(operacaoValue.getId());

        //fato
        final FatoModel fatoModel = fatoAssembler.toModel(operacaoValue);
        operacaoModel.setFato(fatoModel);

        //movimentacoes
        final Collection<Movimentacao> movimentacoes =
            operacaoValue.getMovimentacoes();

        final CollectionModel<MovimentacaoModel> movimentacaoModel =
            movimentacoesAssembler.toCollectionModel(movimentacoes);

        movimentacaoModel.add(
            linkTo(
                Movimentacoes.class,operacaoValue.getId()
            ).withSelfRel()
        );

        operacaoModel.setMovimentacoes(movimentacaoModel);
    }
}
