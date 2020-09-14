package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class ItemDeExtrato implements Serializable{

    private static final long serialVersionUID = 7915709116381425166L;

    private FatoVO fato;

    private int valor;

    public ItemDeExtrato() {
    }

    public ItemDeExtrato(final FatoVO fato, final int valor) {
        this.fato = fato;
        this.valor = valor;
    }

    public ItemDeExtrato(
    		final LocalDate dia,
    		final String descricao,
    		final int valor) {
        this(new FatoVO(dia, descricao),valor);
    }

    public FatoVO getFato() {
        return fato;
    }

    public void setFato(final FatoVO fato) {
        this.fato = fato;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(final int valor) {
        this.valor = valor;
    }
}