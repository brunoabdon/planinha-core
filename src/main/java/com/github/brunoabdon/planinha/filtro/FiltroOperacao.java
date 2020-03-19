package com.github.brunoabdon.planinha.filtro;

import java.io.Serializable;
import java.time.LocalDate;

public class FiltroOperacao implements Serializable{

    private static final long serialVersionUID = -2823304175074337261L;

	private LocalDate dataMinima;

    private LocalDate dataMaxima;

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

	@Override
	public String toString() {
		return "[FiltroOpera|"+dataMinima + "->" + dataMaxima +"]";
	}
}
