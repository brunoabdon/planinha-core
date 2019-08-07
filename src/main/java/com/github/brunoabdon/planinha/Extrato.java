package com.github.brunoabdon.planinha;

import java.io.Serializable;
import java.util.List;

import com.github.brunoabdon.commons.util.modelo.Identifiable;
import com.github.brunoabdon.commons.util.modelo.Periodo;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.gastoso.Fato;

public class Extrato implements Identifiable<Extrato.Id>, Serializable{

    private static final long serialVersionUID = 3530634103009951958L;

    public static class Id implements Serializable{
        
        private static final long serialVersionUID = 3587349119460038562L;
        
        private Conta conta;
        private Periodo periodo;

        public Id(final Conta conta, final Periodo periodo) {
            this.conta = conta;
            this.periodo = periodo;
        }

        public Periodo getPeriodo() {
            return periodo;
        }

        public void setPeriodo(Periodo periodo) {
            this.periodo = periodo;
        }

        public Conta getConta() {
            return conta;
        }

        public void setConta(Conta conta) {
            this.conta = conta;
        }
    }
    
    public static class Item implements Serializable{

        private static final long serialVersionUID = 7915709116381425166L;

        private Fato fato;
        
        private int valor;

        public Item() {
        }
        
        public Item(final Fato fato, final int valor) {
            this.fato = fato;
            this.valor = valor;
        }
        
        public Fato getFato() {
            return fato;
        }

        public void setFato(Fato fato) {
            this.fato = fato;
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }
    }
    
    private Id id;
    
    private int saldoAnterior;
    
    private List<Item> items;
    
    public Extrato(final Id id) {
        this.id = id;
    }

    public Extrato(
            final Id id, 
            final int saldoAnterior, 
            final List<Item> itens) {
        this(id);
        this.saldoAnterior = saldoAnterior;
        this.items = itens;
    }

    
    @Override
    public Id getId() {
        return this.id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(int saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }
}
