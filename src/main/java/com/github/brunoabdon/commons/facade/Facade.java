package com.github.brunoabdon.commons.facade;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.github.brunoabdon.commons.modelo.Identifiable;

/**
 * Uma facade para persistência e possíveis métodos de negócio de uma entidade.
 * @author bruno
 *
 * @param <X> O tipo VO da entidade.
 * @param <K> A chave do tipo {@code V}.
 * @param <F> O tipo do filtro de consulta pra a entidade.
 * @param <A> O tipo de uma atualização da entidade.
 */
public interface Facade <X extends Identifiable<K>, K, F, A >{

    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public X cria(@NotNull @Valid final X elemento)
        throws BusinessException;

    public X pega(@NotNull @Valid final K key)
        throws EntidadeInexistenteException;

    @Transactional(readOnly = true)
    public Page<X> lista(final F filtro, final Pageable pageable)
            throws EntidadeInexistenteException;

    @Transactional(readOnly = true)
    public default Page<X> lista() throws EntidadeInexistenteException{
        return this.lista(null, Pageable.unpaged());
    }

    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public X atualiza(
            @NotNull @Valid final K key,
            @NotNull @Valid final A atualizacao)
        throws BusinessException;

    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public void deleta(@NotNull @Valid final K key)
        throws BusinessException;
}
