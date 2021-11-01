package com.github.brunoabdon.commons.rest.modelfiller;

public interface ModelFiller<M,V> {

    void fillModel(final M model, final V value);
}
