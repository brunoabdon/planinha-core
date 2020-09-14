package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FatoVO implements Serializable {

    private static final long serialVersionUID = -6816326120547843562L;

    private static final int TAMANHO_MAX_DESCRICAO_FATO = 70;

    @NotNull(message = "planinha.valid.fato.semDia")
    private LocalDate dia;

    @Size(
        max = TAMANHO_MAX_DESCRICAO_FATO,
        message = "planinha.valid.fato.descricao.max"
    )
    @NotBlank(message = "planinha.valid.fato.descricao.sem")
    private String descricao;

    public FatoVO() {
        super();
    }

    public FatoVO(
            final LocalDate dia,
            final String descricao) {
        this();
        this.dia = dia;
        this.descricao = descricao;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(final LocalDate dia) {
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
        return "[Fato:" + dia + "|" + descricao + "]";
    }
}
