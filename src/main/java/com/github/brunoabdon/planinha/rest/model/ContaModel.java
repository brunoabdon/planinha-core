package com.github.brunoabdon.planinha.rest.model;

import java.io.Serializable;

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
@Relation(collectionRelation = "contas", itemRelation = "conta")
public class ContaModel extends RepresentationModel<ContaModel>
        implements Serializable{

    private static final long serialVersionUID = -5817506623153687777L;

    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Exclude
    private String nome;

}
