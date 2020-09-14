package com.github.brunoabdon.commons.facade.mappers;

import java.io.Serializable;

import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;

public class IdMappingException extends EntidadeInexistenteException {

    private static final long serialVersionUID = 5848033307914949946L;

    public IdMappingException(final Class<?> tipo, final Serializable id) {
        super(tipo, id);
    }
}
