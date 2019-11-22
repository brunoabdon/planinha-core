package com.github.brunoabdon.planinha.modelo;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;


/**
 * Um evento no mundo, que acarretará em valores serem movimentados entre 
 * {@linkplain Conta contas}.
 * 
 * @author bruno
 */
@Entity
public class Fato extends EntidadeBaseInt {

    private static final long serialVersionUID = 3937390897430670267L;

    public static final int DESC_MAX_LEN = 70;
    
    @Column(nullable = false)
    private LocalDate dia;
    
    @Column(length = DESC_MAX_LEN, nullable = false, unique = false)
    private String descricao;
    
    public Fato() {
    }

    public Fato(final Integer id) {
        super.setId(id);
    }
    
    public Fato(final LocalDate dia, final String descricao) {
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
    
    public static Fato fromString(final String str){
        return EntidadeBaseInt.fromString(Fato.class, str);
    }

    @Override
    public String toString() {
        return "[Fato:" + (dia == null? null : dia.toString())
                + " - " + descricao + "]";
    }

    @Override
    public boolean equals(final Object obj) {
        boolean equal = obj != null && (obj instanceof Fato);
        if(equal){
            final Fato fato = (Fato) obj;
            equal = 
                Objects.equals(this.getId(), fato.getId())
                && Objects.equals(this.getDescricao(), fato.getDescricao())
                && Objects.equals(this.getDia(), fato.getDia());
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(79, 79)
            .append(getId())
            .append(getDia())
            .append(getDescricao())
            .toHashCode();
    }
}