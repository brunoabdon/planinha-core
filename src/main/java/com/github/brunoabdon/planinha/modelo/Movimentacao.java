package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.Identifiable;

@Entity
@Table(name = "lancamento")
public class Movimentacao implements Identifiable<Lancamento.Id>, Serializable {

	private static final long serialVersionUID = 8987967033928269546L;

	@Id
	@JsonbTransient
	private Lancamento.Id id;
	
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
    private Conta conta;
	
	@Column(insertable = false, updatable = false)
	private int valor; 

	public Movimentacao() {
	}
	
	public Movimentacao(final Conta conta, final int valor){
		this.conta = conta;
		this.valor = valor;
	}
	
	@Override
	public Lancamento.Id getId() {
	    return id;
	}
	
	public int getValor() {
		return valor;
	}

	public void setValor(final int valor) {
		this.valor = valor;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(final Conta conta) {
		this.conta = conta;
	}
}