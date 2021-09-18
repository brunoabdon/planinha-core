package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.Fatos;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Component
public class FatoAssembler implements RepresentationModelAssembler<Operacao, FatoModel> {

    @Override
    public FatoModel toModel(final Operacao operacao) {

        final FatoVO fato = operacao.getFato();

        return
            new FatoModel(fato.getDia(),fato.getDescricao())
            .add(
                linkTo(Fatos.class, operacao.getId()).withSelfRel()
            );
    }
}
