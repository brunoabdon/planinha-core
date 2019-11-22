package com.github.brunoabdon.planinha.facade.patch;

import java.time.LocalDate;

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
    
}
