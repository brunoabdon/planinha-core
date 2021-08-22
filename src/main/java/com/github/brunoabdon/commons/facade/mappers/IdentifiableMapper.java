package com.github.brunoabdon.commons.facade.mappers;

import java.io.Serializable;

import com.github.brunoabdon.commons.modelo.Identifiable;

/**
 * Um {@link Mapper} entre dois {@link Identifiable}s.
 *
 * @param <T> O tipo {@link Identifiable} que será mapeado.
 * @param <I> O tipo da {@linkplain Identifiable#getKey() chave} do {@link
 * Identifiable} {@code T}.
 * @param <V> O tipo {@link Identifiable} ao qual será mapeado.
 * @param <J> O tipo da {@linkplain Identifiable#getKey() chave} do {@link
 * Identifiable} {@code V}.
 *
 * @author Bruno Abdon
 */
public interface IdentifiableMapper<
                    T extends Identifiable<I>,
                    I extends Serializable,
                    V extends Identifiable<J>,
                    J extends Serializable
                > extends Mapper<T, V> {

    /**
     * Diz qual é a {@linkplain Identifiable#getKey() chave} do {@link
     * Identifiable} de saída {@code V} a partir da chave do de entrada, {@code
     * T}.
     * @param j Um {@code J}.
     * @return uma chave de {@code T}.
     * @throws IdMappingException se não for possível determinar a chave do
     * {@link Identifiable}.
     */
    I toKey(final J j) throws IdMappingException;
}
