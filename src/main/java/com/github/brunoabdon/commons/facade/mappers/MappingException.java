package com.github.brunoabdon.commons.facade.mappers;

import lombok.Getter;

@Getter
public class MappingException extends RuntimeException {

    private static final long serialVersionUID = -2319006618674023596L;

    private static final String MESSAGE = "Erro mapear %s em %s: %s.";

    private final Class<?> tipoOrigem;

    private final Class<?> tipoDestino;

    public MappingException(
            final Throwable cause,
            final Class<?> tipoOrigem,
            final Class<?> tipoDestino,
            final String msg) {
        super(String.format(MESSAGE,tipoOrigem, tipoDestino, msg), cause);
        this.tipoOrigem = tipoOrigem;
        this.tipoDestino = tipoDestino;
    }
}
