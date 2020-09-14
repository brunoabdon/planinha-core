package com.github.brunoabdon.planinha.dal.modelo;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;


/**
 * Um evento no mundo, que acarretará em valores serem movimentados entre
 * {@linkplain Conta contas}.
 *
 * @author bruno
 */
@NamedQuery(
    name = "Fato.porPeriodo",
    query = "select f from Fato f where f.dia between :dataInicio and :dataFim"
)
@Entity
@Table(schema = "planinhacore")
public class Fato extends EntidadeBaseInt {

    private static final int TAMANHO_MAX_DESCRICAO = 70;

    private static final long serialVersionUID = -1303184765328707216L;

    @Column(nullable = false)
    private LocalDate dia;

    @Column(nullable = false, length = TAMANHO_MAX_DESCRICAO)
    private String descricao;

    @OneToMany(mappedBy = "fato", fetch = LAZY, cascade = {REMOVE, PERSIST})
    private List<Lancamento> lancamentos;

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

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(final List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @Override
    public String toString() {
        return "[Fato:"+getId() + "|" + dia + "|" + descricao + "]";
    }
}