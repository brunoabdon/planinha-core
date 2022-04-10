package com.github.brunoabdon.planinha.dal.modelo;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.EntidadeDeIdInteger;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Um evento no mundo, que acarretará em valores serem movimentados entre
 * {@linkplain Conta contas}.
 *
 * @author bruno
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "planinhacore")
public class Fato extends EntidadeDeIdInteger {

    private static final int TAMANHO_MAX_DESCRICAO = 70;

    private static final long serialVersionUID = -1303184765328707216L;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false)
    private LocalDate dia;

    @EqualsAndHashCode.Exclude
    @Column(nullable = false, length = TAMANHO_MAX_DESCRICAO)
    private String descricao;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "fato", fetch = LAZY, cascade = {REMOVE, PERSIST})
    private List<Lancamento> lancamentos;

    public Fato(final LocalDate dia, final String descricao){
        this.dia = dia;
        this.descricao = descricao;
    }

    public Fato(final Integer id) {
        super(id);
    }

    @Override
    public String toString() {
        return "[Fato:"+getId() + "|" + dia + "|" + descricao + "]";
    }
}