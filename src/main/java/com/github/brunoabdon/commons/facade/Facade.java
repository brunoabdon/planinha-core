package com.github.brunoabdon.commons.facade;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    public List<X> lista(final F filtro) throws EntidadeInexistenteException;

    public default List<X> listar() throws EntidadeInexistenteException{
        return this.lista(null);
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
