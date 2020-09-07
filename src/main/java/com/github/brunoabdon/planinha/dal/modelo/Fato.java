package com.github.brunoabdon.planinha.dal.modelo;

import static javax.persistence.CascadeType.REMOVE;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;


/**
 * Um evento no mundo, que acarretará em valores serem movimentados entre
 * {@linkplain Conta contas}.
 *
 * @author bruno
 */
@Entity
@Table(schema = "planinhacore")
public class Fato extends EntidadeBaseInt {

    private static final int TAMANHO_MAX_DESCRICAO_FATO = 70;

    private static final long serialVersionUID = -1303184765328707216L;

    @Column(nullable = false)
    private LocalDate dia;

    @Column(nullable = false, length = TAMANHO_MAX_DESCRICAO_FATO)
    private String descricao;

    @OneToMany(mappedBy = "fato",cascade = REMOVE)
    private List<Lancamento> movimentacoes;

    public Fato() {
    }

    public Fato(final LocalDate dia, final String descricao){
        this.dia = dia;
        this.descricao = descricao;
    }

    public Fato(final Integer id) {
        super(id);
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
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "[Fato:"+getId() + "|" + dia + "|" + descricao + "]";
    }
}