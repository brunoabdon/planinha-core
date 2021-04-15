package com.github.brunoabdon.commons.rest.assembler;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.github.brunoabdon.commons.modelo.Identifiable;

public abstract class IdentifiableModelAssembler<
    I extends Identifiable<?>,
    D extends RepresentationModel<?>>
        extends RepresentationModelAssemblerSupport<I, D>{

    protected IdentifiableModelAssembler(
        final Class<?> controllerClass, final Class<D> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public D toModel(final I identifiable) {

        final D model =
            createModelWithId(identifiable.getId(),identifiable);

        fillModel(model, identifiable);

        return model;
    }

    protected abstract void fillModel(D model, I identifiable);

}