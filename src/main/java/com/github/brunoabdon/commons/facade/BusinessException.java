package com.github.brunoabdon.commons.facade;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends Exception {

    private static final long serialVersionUID = 1992627921824744312L;

    public BusinessException(final String message) {
        super(message);
    }
}
