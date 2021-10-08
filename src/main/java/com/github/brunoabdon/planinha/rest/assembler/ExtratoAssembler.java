package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.rest.Extratos;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import com.github.brunoabdon.planinha.rest.model.ExtratoModel;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Setter(PACKAGE)
public class ExtratoAssembler implements RepresentationModelAssembler<Extrato, ExtratoModel> {

    @Autowired
    private IdentifiableModelAssembler<ContaVO, ContaModel> contaAssembler;

    @Autowired
    private Converter<Periodo, String> periodoConverter;

    @Override
    @SneakyThrows
    public final ExtratoModel toModel(@NonNull final Extrato extrato) {

        final Integer contaId = extrato.getConta().getId();
        final Periodo periodo = extrato.getPeriodo();

        final String periodoStr = periodoConverter.convert(periodo);

        final Link selfRel =
            linkTo(
                methodOn(Extratos.class).pegar(contaId,periodoStr)
            ).withSelfRel();

        return createModel(extrato).add(selfRel);
    }

    private ExtratoModel createModel(final Extrato extrato) {
        final ContaVO conta = extrato.getConta();

        final ContaModel contaModel = contaAssembler.toModel(conta);

        final Periodo periodo = extrato.getPeriodo();

        final String id = periodoConverter.convert(periodo);

        return
            new ExtratoModel(
                id,
                extrato.getSaldoAnterior(),
                periodo,
                contaModel
            );
    }

    @Override
    public CollectionModel<ExtratoModel> toCollectionModel(Iterable<? extends Extrato> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
