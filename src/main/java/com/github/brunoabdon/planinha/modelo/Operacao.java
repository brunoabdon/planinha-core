package com.github.brunoabdon.planinha.modelo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Operacao extends EntidadeBaseInt {

	private static final long serialVersionUID = 7410023178167290123L;

	@NotNull
	@EqualsAndHashCode.Exclude
	private FatoVO fato;

	@NotEmpty
    @EqualsAndHashCode.Exclude
    private List<Movimentacao> movimentacoes;

    public Operacao(
            final Integer id,
            final FatoVO fato,
            final List<Movimentacao> movimentacoes) {
        super(id);
        this.fato = fato;
        this.movimentacoes = movimentacoes;
    }
	@Override
	public String toString() {
		return "[Operacao:"+getId()+"|"+ fato + "]";
	}
}
