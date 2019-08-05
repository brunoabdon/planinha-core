package com.github.brunoabdon.planinha;

import java.util.List;

import com.github.brunoabdon.commons.modelo.Entidade;
import com.github.brunoabdon.gastoso.Fato;

public class Operacao implements Entidade<Fato> {

	private static final long serialVersionUID = 7410023178167290123L;

	private Fato fato;
	private List<Movimentacao> movimentacoes;
	
	@Override
	public Fato getId() {
		return this.getFato();
	}

	@Override
	public void setId(final Fato id) {
		this.setFato(id);

	}

	public Fato getFato() {
		return fato;
	}

	public void setFato(Fato fato) {
		this.fato = fato;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	@Override
	public int hashCode() {
		return fato == null ? 0 : fato.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operacao other = (Operacao) obj;
		if (fato == null) {
			if (other.fato != null)
				return false;
		} else if (!fato.equals(other.fato))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "[Operacao:"+ fato + "]";
	}

}
