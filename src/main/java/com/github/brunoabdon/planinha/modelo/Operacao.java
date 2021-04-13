package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.brunoabdon.commons.modelo.Identifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operacao
        implements Identifiable<Integer>, Serializable {

	private static final long serialVersionUID = 7410023178167290123L;

    @NotNull
    @EqualsAndHashCode.Include
    private Integer id;

	@NotNull
	@EqualsAndHashCode.Exclude
	private FatoVO fato;

	@NotEmpty
    @EqualsAndHashCode.Exclude
    private List<Movimentacao> movimentacoes;

	@Override
	public String toString() {
		return "[Operacao:"+getId()+"|"+ fato + "]";
	}
}
