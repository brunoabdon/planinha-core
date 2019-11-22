package com.github.brunoabdon.commons.facade;

import java.util.List;

import javax.transaction.Transactional;

import com.github.brunoabdon.commons.modelo.Identifiable;

public interface Facade <X extends Identifiable<K>,K,F,A>{

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public X cria(final X elemento) throws BusinessException;
    
    public X pega(final K key) throws EntidadeInexistenteException;
    
    public List<X> listar(final F filtro);
    
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public X atualiza(final K key, final A atualizacao) 
        throws EntidadeInexistenteException, BusinessException;

    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(final K key) 
        throws EntidadeInexistenteException, BusinessException;
}
