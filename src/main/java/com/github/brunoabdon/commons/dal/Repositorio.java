package com.github.brunoabdon.commons.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.brunoabdon.commons.modelo.Identifiable;

public interface Repositorio<E extends Identifiable<I>, I>
            extends JpaRepository<E, I> {

}
