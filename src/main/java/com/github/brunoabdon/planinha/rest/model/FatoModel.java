package com.github.brunoabdon.planinha.rest.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Relation(collectionRelation = "fatos", itemRelation = "fato")
public class FatoModel extends RepresentationModel<FatoModel> implements Serializable {

    private static final long serialVersionUID = 9076100039056128268L;

    private LocalDate dia;

    private String descricao;
}
