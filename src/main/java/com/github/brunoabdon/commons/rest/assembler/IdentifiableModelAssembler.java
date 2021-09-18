package com.github.brunoabdon.commons.rest.assembler;

import com.github.brunoabdon.commons.modelo.Identifiable;
import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public abstract class IdentifiableModelAssembler<
    I extends Identifiable<?>,
    D extends RepresentationModel<?>>
        extends RepresentationModelAssemblerSupport<I, D>{

    @Autowired
    private ModelFiller<D, I> modelFiller;

    protected IdentifiableModelAssembler(
            final Class<?> controllerClass,
            final Class<D> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public D toModel(final I identifiable) {

        final D model =
            createModelWithId(identifiable.getId(),identifiable);

        modelFiller.fillModel(model, identifiable);

        return model;
    }
}