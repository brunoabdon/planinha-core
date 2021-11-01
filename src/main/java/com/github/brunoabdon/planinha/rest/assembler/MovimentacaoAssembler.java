package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.ChildrenRepresentationModelAssembler;
import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.Movimentacoes;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MovimentacaoAssembler implements ChildrenRepresentationModelAssembler<Operacao,Movimentacao, MovimentacaoModel> {

    @Autowired
    @Setter(PACKAGE)
    private RepresentationModelsAssembler<ContaVO, ContaModel> contaAssembler;

    @Override
    public MovimentacaoModel toModel(
            final Operacao parent, final Movimentacao movimentacao) {
        final ContaVO conta = movimentacao.getConta();
        final ContaModel contaModel = contaAssembler.toModel(conta);
        final int valor = movimentacao.getValor();

        return
            new MovimentacaoModel(valor,contaModel)
            .add(
                linkToListInParent(parent).slash(conta.getId()).withSelfRel()
            );
    }

    @Override
    public CollectionModel<MovimentacaoModel> toCollectionModel(
            final Operacao operacao,
            final Iterable<? extends Movimentacao> movimentacaos) {

        return
            ChildrenRepresentationModelAssembler.super.toCollectionModel(operacao, movimentacaos)
            .add(
                linkToListInParent(operacao).withSelfRel()
            );

    }

    private WebMvcLinkBuilder linkToListInParent(final Operacao operacao) {
        return linkTo(Movimentacoes.class, operacao.getId());
    }
}
