package com.github.brunoabdon.commons.facade;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.brunoabdon.commons.modelo.Identifiable;

/**
 * Uma facade para persistência e possíveis métodos de negócio de uma entidade,
 * levando em consideração sua transformação em um VO.
 * @author bruno
 *
 * @param <E> O tipo da entidade no banco.
 * @param <I> A chave do tipo da entidade {@code E}.
 * @param <X> O tipo VO da entidade.
 * @param <K> A chave do tipo {@code V}.
 * @param <F> O tipo do filtro de consulta pra a entidade.
 * @param <A> O tipo de uma atualização da entidade.
 */
public interface Facade <X extends Identifiable<K>, K, F, A >{

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public X cria(@NotNull @Valid final X elemento)
        throws BusinessException, EntidadeInexistenteException;

    public X pega(@NotNull @Valid final K key)
        throws EntidadeInexistenteException;

    public List<X> lista(final F filtro) throws EntidadeInexistenteException;

    public default List<X> listar() throws EntidadeInexistenteException{
        return this.lista(null);
    }

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public X atualiza(
            @NotNull @Valid final K key,
            @NotNull @Valid final A atualizacao)
        throws EntidadeInexistenteException, BusinessException;

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(@NotNull @Valid final K key)
        throws EntidadeInexistenteException, BusinessException;
}
