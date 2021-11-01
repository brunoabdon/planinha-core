package com.github.brunoabdon.commons.rest.assembler;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public abstract class RepresentationModelsAssemblerSupport<T, D extends RepresentationModel<?>>
        extends RepresentationModelAssemblerSupport<T,D>
        implements RepresentationModelsAssembler<T,D> {
    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     * @param controllerClass must not be {@literal null}.
     * @param resourceType    must not be {@literal null}.
     */
    public RepresentationModelsAssemblerSupport(
            final Class<?> controllerClass, final Class<D> resourceType) {
        super(controllerClass, resourceType);
    }
}
