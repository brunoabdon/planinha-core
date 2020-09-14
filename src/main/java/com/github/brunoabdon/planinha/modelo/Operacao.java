package com.github.brunoabdon.planinha.modelo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;

public class Operacao extends EntidadeBaseInt {

	private static final long serialVersionUID = 7410023178167290123L;

	@NotNull
	private FatoVO fato;

	@NotEmpty
    private List<Movimentacao> movimentacoes;

	public Operacao() {
	}

    public Operacao(
            final Integer id,
            final FatoVO fato,
            final List<Movimentacao> movimentacoes) {
        super(id);
        this.fato = fato;
        this.movimentacoes = movimentacoes;
    }

    public FatoVO getFato() {
		return fato;
	}

	public void setFato(final FatoVO fato) {
		this.fato = fato;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(final List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	@Override
	public String toString() {
		return "[Operacao:"+getId()+"|"+ fato + "]";
	}
}
