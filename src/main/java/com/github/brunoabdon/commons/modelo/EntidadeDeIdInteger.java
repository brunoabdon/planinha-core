package com.github.brunoabdon.commons.modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe base pra {@link Entidade}s com {@link Entidade#getId() id} do tipo
 * {@link Integer}.
 *
 * @author bruno
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@MappedSuperclass
public class EntidadeDeIdInteger implements Entidade<Integer> {

    private static final long serialVersionUID = 5533865547017401869L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}