package com.github.brunoabdon.planinha.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import com.github.brunoabdon.planinha.rest.model.ContaModel;

//TODO agrupar os Controlers com os Processors em pacotes.
@Component
public class ExtratosDaContaProcessor
            implements RepresentationModelProcessor<ContaModel> {

    @Override
    public ContaModel process(final ContaModel model) {

        final Integer idConta = model.getId();

        model.add(
            linkTo(Extratos.class, idConta).withRel("extratos")
        );

        return model;
    }
}
