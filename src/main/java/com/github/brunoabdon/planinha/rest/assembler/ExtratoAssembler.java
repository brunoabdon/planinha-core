package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.rest.Extratos;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import com.github.brunoabdon.planinha.rest.model.ExtratoModel;
import com.github.brunoabdon.planinha.rest.model.ItemDeExtratoModel;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Setter(PACKAGE)
public class ExtratoAssembler implements RepresentationModelsAssembler<Extrato, ExtratoModel> {

    @Autowired
    private IdentifiableModelAssembler<ContaVO, ContaModel> contaAssembler;

    @Autowired
    private RepresentationModelsAssembler<ItemDeExtrato, ItemDeExtratoModel>
        itemDeExtratoAssembler;

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

        return toSimpleModel(extrato).add(selfRel);
    }

    @Override
    public ExtratoModel toFullModel(final Extrato extrato) {
        final ExtratoModel model = toModel(extrato);
        model.setSaldoAnterior(extrato.getSaldoAnterior());

        final List<ItemDeExtrato> items = extrato.getItems();

        final CollectionModel<ItemDeExtratoModel> itensModel =
            itemDeExtratoAssembler.toCollectionModel(items);

        model.setItens(itensModel);
        return model;
    }

    private ExtratoModel toSimpleModel(final Extrato extrato) {
        final ContaVO conta = extrato.getConta();

        final ContaModel contaModel = contaAssembler.toModel(conta);

        final Periodo periodo = extrato.getPeriodo();

        final String id = periodoConverter.convert(periodo);

        return
            new ExtratoModel(
                id,
                null,
                periodo,
                contaModel,
                null
            );
    }
}
