package com.github.brunoabdon.planinha;

import com.github.brunoabdon.gastoso.Conta;

public class Movimentacao {

	private Conta conta;
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
