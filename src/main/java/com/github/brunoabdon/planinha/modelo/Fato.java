package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;


/**
 * Um evento no mundo, que acarretará em valores serem movimentados entre
 * {@linkplain Conta contas}.
 *
 * @author bruno
 */
@Embeddable
public class Fato implements Serializable {

	private static final long serialVersionUID = -1303184765328707216L;

    private LocalDate dia;

    private String descricao;

    public Fato() {
    }

    public Fato(final LocalDate dia, final String descricao){
        this.dia = dia;
        this.descricao = descricao;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "["+dia+"|"+descricao+"]";
    }
}