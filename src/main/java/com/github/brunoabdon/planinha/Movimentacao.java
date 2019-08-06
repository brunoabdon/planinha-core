package com.github.brunoabdon.planinha;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.github.brunoabdon.gastoso.Conta;

@Entity
public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 8987967033928269546L;

	@Id
	@JsonbTransient
	private Integer id;
	
	@MapsId
	@ManyToOne
    private Conta conta;
	
	@Column(insertable = false, updatable = false)
	private int valor; 

	public Movimentacao() {
	}
	
	public Movimentacao(final Conta conta, final int valor) {
		this.conta = conta;
		this.valor = valor;
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
