package com.github.brunoabdon.planinha.rest.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "movimentacoes", itemRelation = "movimentacao")
public class MovimentacaoModel extends RepresentationModel<MovimentacaoModel> implements Serializable {

    private static final long serialVersionUID = -7901188884085707429L;

    @EqualsAndHashCode.Exclude
    private int valor;

    @EqualsAndHashCode.Exclude
    private ContaModel conta;

}
