package com.github.brunoabdon.commons.facade.mappers;

/**
 * Um bean que sabe mapear um {@code T} em um {@code V}.
 *
 * @param <T> O tipo que será mapeado.
 * @param <V> O tipo ao qual será mapeado.
 *
 * @author Bruno Abdon
 */
public interface Mapper<T,V> {

    /**
     * Transforma um {@code T} em um {@code V}.
     * @param t um {@code T}
     * @return um v.
     */
    V toVO(final T t);

    /**
     * Transforma um {@code T} na versão simplificada (com menos atributos) de
     * um {@code V}. A implentação default delega a {@link #toVO(Object)
     * toVO(...)}.
     * @param t um {@code T}
     * @return um {@code V} simplificado.
     */
    default V toVOSimples(final T t) {
        return toVO(t);
    }
}
