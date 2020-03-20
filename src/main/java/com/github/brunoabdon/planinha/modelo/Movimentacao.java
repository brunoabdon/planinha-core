package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import com.github.brunoabdon.commons.modelo.Identifiable;


public class Movimentacao
		implements Identifiable<MovimentacaoId>, Serializable {

	private static final long serialVersionUID = 8987967033928269546L;

	@EmbeddedId
	@JsonbTransient
	private MovimentacaoId id;

	@Column(insertable = false, updatable = false)
	private int valor;

	public Movimentacao() {
	}

	@Override
	public MovimentacaoId getId() {
	    return id;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(final int valor) {
		this.valor = valor;
	}
}