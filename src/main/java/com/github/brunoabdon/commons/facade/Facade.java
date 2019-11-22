package com.github.brunoabdon.commons.facade;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.github.brunoabdon.commons.modelo.Identifiable;

public interface Facade <X extends Identifiable<K>,K,F,A>{

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public X cria(@NotNull @Valid final X elemento) throws BusinessException;
    
    public X pega(@NotNull @Valid final K key) 
        throws EntidadeInexistenteException;
    
    public List<X> listar(final F filtro);

    public default List<X> listar(){
        return this.listar(null);
    }
    
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public X atualiza(@
            NotNull @Valid final K key, 
            @NotNull @Valid final A atualizacao) 
        throws EntidadeInexistenteException, BusinessException;

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(@NotNull @Valid final K key) 
        throws EntidadeInexistenteException, BusinessException;
}
