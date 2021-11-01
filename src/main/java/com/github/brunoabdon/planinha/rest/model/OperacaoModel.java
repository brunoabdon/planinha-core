package com.github.brunoabdon.planinha.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "operacoes", itemRelation = "operacao")
public class OperacaoModel extends RepresentationModel<OperacaoModel>
        implements Serializable {

    private static final long serialVersionUID = 3807782862001395553L;

    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Exclude
    private FatoModel fato;

    @JsonInclude(NON_ABSENT)
    @EqualsAndHashCode.Exclude
    private CollectionModel<MovimentacaoModel> movimentacoes;

}
