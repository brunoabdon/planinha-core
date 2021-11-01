package com.github.brunoabdon.commons.rest.assembler;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public interface RepresentationModelsAssembler<T,D extends RepresentationModel<?>> extends RepresentationModelAssembler<T,D> {

    default D toFullModel(final T entity){
        return toModel(entity);
    }
}
