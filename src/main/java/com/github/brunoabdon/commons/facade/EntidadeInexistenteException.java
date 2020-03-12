package com.github.brunoabdon.commons.facade;

import java.io.Serializable;

public class EntidadeInexistenteException extends Exception {

    private static final long serialVersionUID = 2749804844713036560L;

    private static final String ERRO_NOT_FOUND = 
        "com.github.brunoabdon.commons.notfound.%s.%s";

    private final Serializable id;
    
    public EntidadeInexistenteException(
            final Class<?> tipo, 
            final Serializable id) {
        super(String.format(ERRO_NOT_FOUND,tipo.getName(),id));
        this.id = id;
    }
    
    public Serializable getId() {
        return id;
    }    
}
