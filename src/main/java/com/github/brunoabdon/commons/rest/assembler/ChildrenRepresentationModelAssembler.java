package com.github.brunoabdon.commons.rest.assembler;

import com.github.brunoabdon.commons.modelo.Identifiable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public interface ChildrenRepresentationModelAssembler<P extends Identifiable<?>, T, D extends RepresentationModel<?>>{

    D toModel(final P parent, final T entity);

    default CollectionModel<D> toCollectionModel(
            final P parent,
            final Iterable<? extends T> entities) {

        return StreamSupport.stream(entities.spliterator(), false) //
                .map(e -> toModel(parent,e)) //
                .collect(collectingAndThen(toList(), CollectionModel::of));
    }
}
