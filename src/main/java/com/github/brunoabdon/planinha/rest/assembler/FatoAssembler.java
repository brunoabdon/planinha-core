package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.Fatos;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Component
public class FatoAssembler extends IdentifiableModelAssembler<Operacao, FatoModel> {

    protected FatoAssembler() {
        super(Fatos.class, FatoModel.class);
    }

    @Override
    protected FatoModel createModelWithId(
            final Object id,
            final Operacao operacao,
            final Object... parameters) {

        Assert.notNull(operacao, "Operação não pode ser nula!");
        Assert.notNull(id, "Id must not be null!");

        final FatoModel fatoModel = instantiateModel(operacao);

        final Link self = buildSelfLink(operacao);

        return fatoModel.add(self);
    }

    private Link buildSelfLink(final Operacao operacao) {
        return linkTo(Fatos.class,operacao.getId()).withSelfRel();
    }
}
