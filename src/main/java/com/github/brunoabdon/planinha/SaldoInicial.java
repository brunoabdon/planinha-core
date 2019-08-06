package com.github.brunoabdon.planinha;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.github.brunoabdon.commons.modelo.Entidade;
import com.github.brunoabdon.gastoso.Conta;

@Entity
public class SaldoInicial implements Entidade<SaldoInicial.Id>{

    private static final long serialVersionUID = -5673743843234529201L;

    @Embeddable
    public static class Id implements Serializable{
        
        private static final long serialVersionUID = -1629761207891255966L;
        
        public Id() {
            
        }
        public Id(final Conta conta, final LocalDate dia) {
            super();
            this.conta = conta;
            this.dia = dia;
        }
        private Conta conta;
        private LocalDate dia;

        public Conta getConta() {
            return conta;
        }
        public void setConta(Conta conta) {
            this.conta = conta;
        }
        public LocalDate getDia() {
            return dia;
        }
        public void setDia(LocalDate dia) {
            this.dia = dia;
        }
    }

    @EmbeddedId
    private Id id;
    
    private int valor;
    
    public SaldoInicial() {
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setId(final Id id) {
        this.setId(id);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
