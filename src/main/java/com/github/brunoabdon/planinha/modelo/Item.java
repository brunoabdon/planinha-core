package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Item implements Serializable{

    private static final long serialVersionUID = 7915709116381425166L;

    private Fato fato;

    private int valor;

    public Item() {
    }

    public Item(final Fato fato, final int valor) {
        this.fato = fato;
        this.valor = valor;
    }

    public Item(
    		final LocalDate dia,
    		final String descricao,
    		final int valor) {
        this(new Fato(dia, descricao),valor);
    }

    public Fato getFato() {
        return fato;
    }

    public void setFato(Fato fato) {
        this.fato = fato;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}