package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class MovimentacaoId implements Serializable{

	private static final long serialVersionUID = -6601183622592316622L;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
    private Conta conta;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
    private Operacao operacao;

	public MovimentacaoId() {
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(final Conta conta) {
		this.conta = conta;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(final Operacao operacao) {
		this.operacao = operacao;
	}
}
