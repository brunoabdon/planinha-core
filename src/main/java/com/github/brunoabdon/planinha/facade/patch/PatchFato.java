package com.github.brunoabdon.planinha.facade.patch;

import java.time.LocalDate;

import com.github.brunoabdon.planinha.modelo.Fato;
import com.github.brunoabdon.planinha.modelo.Operacao;

public class PatchFato {

    private LocalDate dia;

    private String descricao;

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

	public void patch(final Operacao operacao) {
		final Fato fato = operacao.getFato();
		fato.setDia(dia);
		fato.setDescricao(descricao);
	}

}
