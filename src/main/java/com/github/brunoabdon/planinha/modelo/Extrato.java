package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.github.brunoabdon.commons.modelo.Identifiable;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.rest.paramconverters.PeriodoParamConverter;

public class Extrato implements Identifiable<Id>, Serializable{

    private static final long serialVersionUID = 3530634103009951958L;

    private static final PeriodoParamConverter EXTRATO_ID_SERIALIZER = 
        new PeriodoParamConverter();

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

    @JsonbTransient
    private Id id;

    private String serialId;

    private Number saldoAnterior;
    
    private List<Item> items;

    
    public Extrato(final Id id) {
        this.id = id;
    }

    public Extrato(
            final Id id, 
            final Number saldoAnterior, 
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

    public Number getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(final Number saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }
    
    public Conta getConta(){
        return getOptionalAttr(Id::getConta).orElse(null);
    }
    
    public LocalDate getDataInicio() {
        return getData(Periodo::getDataMinima);
    }

    public LocalDate getDataFim() {
        return getData(Periodo::getDataMaxima);
    }

    private LocalDate getData(final Function<Periodo, LocalDate> dateMapper) {
        return getOptionalAttr(Id::getPeriodo).map(dateMapper).orElse(null);
    }

    private <T> Optional<? extends T> getOptionalAttr(
            final Function<? super Id, ? extends T> getter) {
        return optionalId().map(getter);
    }
    
    private Optional<Id> optionalId() {
        return Optional.of(id);
    }
    
    @JsonbProperty("id")
    public String getSerialId() {
        if(this.serialId == null) {
            this.serialId=EXTRATO_ID_SERIALIZER.toString(getId().getPeriodo());
        }
        return serialId;
    }
}
