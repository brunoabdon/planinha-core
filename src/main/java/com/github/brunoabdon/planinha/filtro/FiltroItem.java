package com.github.brunoabdon.planinha.filtro;

import java.time.LocalDate;

public class FiltroItem {

	public Integer conta;
	
	public LocalDate dataMinima;
	
	public LocalDate dataMaxima;

	public Integer getConta() {
		return conta;
	}

	public void setConta(Integer conta) {
		this.conta = conta;
	}

	public LocalDate getDataMinima() {
		return dataMinima;
	}

	public void setDataMinima(LocalDate dataMinima) {
		this.dataMinima = dataMinima;
	}

	public LocalDate getDataMaxima() {
		return dataMaxima;
	}

	public void setDataMaxima(LocalDate dataMaxima) {
		this.dataMaxima = dataMaxima;
	}

	
}
