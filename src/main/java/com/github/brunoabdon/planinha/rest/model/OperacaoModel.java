package com.github.brunoabdon.planinha.rest.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "operacoes", itemRelation = "operacao")
public class OperacaoModel extends RepresentationModel<OperacaoModel>
        implements Serializable {

    private static final long serialVersionUID = 3807782862001395553L;

    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Exclude
    private FatoModel fato;

    @EqualsAndHashCode.Exclude
    private CollectionModel<MovimentacaoModel> movimentacoes;

}
