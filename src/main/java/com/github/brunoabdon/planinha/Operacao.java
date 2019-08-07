package com.github.brunoabdon.planinha;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;
import com.github.brunoabdon.gastoso.Fato;

@Entity
@Table(name = "fato")
public class Operacao extends EntidadeBaseInt {

	private static final long serialVersionUID = 7410023178167290123L;

	@MapsId
	@OneToOne
	@JoinColumn(name = "id")
	private Fato fato;

	@OneToMany
	@JoinColumn(name="fato_id")  //fato_id em "movimentacao"
    private List<Movimentacao> movimentacoes;
	
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
