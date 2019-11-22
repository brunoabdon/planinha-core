package com.github.brunoabdon.commons.modelo;

import java.io.Serializable;


/**
 * Um {@link Identifiable} cuja chave pode ser setada.
 * 
 * @param <K> O tipo da chave do identifiable.
 * 
 * @author bruno abdon
 * 
 */
public interface Entidade<K> extends Identifiable<K>, Serializable {

	/**
	 * Seta a chave do elemento.
	 * 
	 * @param id A chave a ser setda.
	 */
    public void setId(final K id);
}