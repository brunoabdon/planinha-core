package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.rest.Movimentacoes;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MovimentacaoAssembler
        implements RepresentationModelsAssembler<Movimentacao, MovimentacaoModel> {

    @Autowired
    @Setter(PACKAGE)
    private RepresentationModelsAssembler<ContaVO, ContaModel> contaAssembler;

    @Override
    public MovimentacaoModel toModel(final Movimentacao movimentacao) {
        final ContaVO conta = movimentacao.getConta();
        final ContaModel contaModel = contaAssembler.toModel(conta);
        final int valor = movimentacao.getValor();

        final Integer parentId = movimentacao.getId().getOperacaoId();

        return
            new MovimentacaoModel(valor,contaModel)
            .add(
                linkToListInParent(parentId).slash(conta.getId()).withSelfRel()
            );
    }

    private WebMvcLinkBuilder linkToListInParent(final Object parentId) {
        return linkTo(Movimentacoes.class, parentId);
    }
}
