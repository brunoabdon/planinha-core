package com.github.brunoabdon.planinha.modelo;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;

@Entity
@Table(schema = "planinhacore", name = "fato")
public class Operacao extends EntidadeBaseInt {

	private static final long serialVersionUID = 7410023178167290123L;

	public static final int DESC_MAX_LEN = 70;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(
			name = "dia",
			column = @Column(nullable = false)
		),
		@AttributeOverride(
			name = "descricao",
			column = @Column(length=DESC_MAX_LEN, nullable=false, unique=false)
		),
	})
	@NotNull
	private Fato fato;

	@OneToMany(
		fetch = EAGER,
		cascade = REMOVE,
		mappedBy = "operacao"
	)
	@NotEmpty
    private List<Lancamento> movimentacoes;

	public Operacao() {
	}

	public Operacao(Integer id) {
		super(id);
	}

	public Fato getFato() {
		return fato;
	}

	public void setFato(final Fato fato) {
		this.fato = fato;
	}

	public List<Lancamento> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(final List<Lancamento> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	@Override
	public int hashCode() {
		return fato == null ? 0 : fato.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
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
