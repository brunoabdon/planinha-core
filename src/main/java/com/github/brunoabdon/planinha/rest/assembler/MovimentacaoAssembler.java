package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.rest.Movimentacoes;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MovimentacaoAssembler extends IdentifiableModelAssembler<Movimentacao, MovimentacaoModel> {

    protected MovimentacaoAssembler() {
        super(Movimentacoes.class, MovimentacaoModel.class);
    }

    @Override
    protected MovimentacaoModel createModelWithId(
            final Object id,
            final Movimentacao movimentacao,
            final Object... parameters) {

        Assert.notNull(movimentacao, "Movimentacao não pode ser nula!");
        Assert.notNull(id, "Id must not be null!");

        final MovimentacaoModel movimentacaoModel = instantiateModel(movimentacao);

        final Link self = buildSelfLink(movimentacao);

        return movimentacaoModel.add(self);
    }

    private Link buildSelfLink(final Movimentacao movimentacao) {
        return linkTo(Movimentacao.class,movimentacao.getId()).withSelfRel();
    }
}
