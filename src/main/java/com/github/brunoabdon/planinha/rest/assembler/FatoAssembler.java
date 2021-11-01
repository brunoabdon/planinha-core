package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.Fatos;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import lombok.Setter;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@Setter(PACKAGE)
public class FatoAssembler implements RepresentationModelsAssembler<Operacao, FatoModel> {

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
