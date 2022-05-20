package com.github.brunoabdon.commons.facade.mappers;

import java.io.Serializable;

import com.github.brunoabdon.commons.modelo.Identifiable;

/**
 * Um {@link IdentifiableMapper} onde as duas chaves têm o mesmo tipo.
 *
 * @param <T> O tipo {@link Identifiable} que será mapeado.
 * @param <V> O tipo {@link Identifiable} ao qual será mapeado.
 * @param <I> O tipo da {@linkplain Identifiable#getId() chave} dos {@link
 * Identifiable}s {@code T} e {@code V}.
 *
 * @author Bruno Abdon - bruno.monteiro@ans.gov.br
 */
public interface SimpleIdentifiableMapper<
                    T extends Identifiable<I>,
                    V extends Identifiable<I>,
                    I extends Serializable
            > extends IdentifiableMapper<T, I, V, I> {

    /**
     * Diz qual é a {@linkplain Identifiable#getId() chave} do {@link
     * Identifiable} de entrada {@code T} a partir da chave do de saida, {@code
     * V}. A implementação default diz que a chave se mantem.
     * @param i a chave do elemento de saida.
     * @return uma chave de {@code T}.
     */
    @Override
    default I toKey(final I i) {
        return i;
    }

}
