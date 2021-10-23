package com.github.brunoabdon.planinha.rest.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "itens")
public class ItemDeExtratoModel extends RepresentationModel<ItemDeExtratoModel> {

    private FatoModel fato;

    private int valor;
}
