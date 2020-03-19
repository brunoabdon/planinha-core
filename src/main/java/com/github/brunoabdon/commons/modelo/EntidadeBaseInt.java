package com.github.brunoabdon.commons.modelo;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe base pra {@link Entidade}s com {@link Entidade#getId() id} do tipo 
 * {@link Integer}.
 * 
 * @author bruno
 */
@MappedSuperclass
public class EntidadeBaseInt implements Entidade<Integer> {

    private static final long serialVersionUID = 5533865547017401869L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public static <X extends EntidadeBaseInt> X fromString(
            final Class<X> klass, final String str) {
        final X entidade;

        try {
            final int id = Integer.parseInt(str);

            entidade = klass.getDeclaredConstructor().newInstance();
            entidade.setId(id);
        } catch (final NumberFormatException e) {
        	throw new RuntimeException("Bad id \"" + str + "\"", e);
        } catch (InstantiationException 
                | IllegalAccessException 
                | IllegalArgumentException 
                | InvocationTargetException 
                | NoSuchMethodException 
                | SecurityException e){
            throw new RuntimeException(e);
        }

        return entidade;
    }
}