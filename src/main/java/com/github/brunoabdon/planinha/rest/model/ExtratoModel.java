package com.github.brunoabdon.planinha.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.brunoabdon.commons.modelo.Periodo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "extratos", itemRelation = "extrato")
public class ExtratoModel extends RepresentationModel<ExtratoModel>
    implements Serializable {

    @EqualsAndHashCode.Include
    private String id;

    @JsonInclude(NON_ABSENT)
    @EqualsAndHashCode.Exclude
    private Number saldoAnterior;

    @EqualsAndHashCode.Exclude
    private Periodo periodo;

    @EqualsAndHashCode.Exclude
    private ContaModel conta;

    @JsonInclude(NON_ABSENT)
    @EqualsAndHashCode.Exclude
    private CollectionModel<ItemDeExtratoModel> itens;

}
