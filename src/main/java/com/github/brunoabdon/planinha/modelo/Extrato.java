package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.github.brunoabdon.commons.modelo.Identifiable;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;

public class Extrato implements Identifiable<Id>, Serializable{

    private static final long serialVersionUID = 3530634103009951958L;

    public static class Id implements Serializable{

        private static final long serialVersionUID = 3587349119460038562L;

        private ContaVO conta;
        private Periodo periodo;

        public Id(final ContaVO conta, final Periodo periodo) {
            this.conta = conta;
            this.periodo = periodo;
        }

        public Periodo getPeriodo() {
            return periodo;
        }

        public void setPeriodo(Periodo periodo) {
            this.periodo = periodo;
        }

        public ContaVO getConta() {
            return conta;
        }

        public void setConta(ContaVO conta) {
            this.conta = conta;
        }

        @Override
        public String toString() {
        	return "["+conta+"|"+periodo+"]";
        }
    }

    @JsonbTransient
    private Id id;

    private String serialId;

    private Number saldoAnterior;

    private List<ItemDeExtrato> items;

    public Extrato() {
    }

    public Extrato(final Id id) {
        this.id = id;
    }

    public Extrato(
            final Id id,
            final Number saldoAnterior,
            final List<ItemDeExtrato> itens) {
        this(id);
        this.saldoAnterior = saldoAnterior;
        this.items = itens;
    }

    @Override
    public Id getId() {
        return this.id;
    }

    public List<ItemDeExtrato> getItems() {
        return items;
    }

    public void setItems(List<ItemDeExtrato> items) {
        this.items = items;
    }

    public Number getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(final Number saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public ContaVO getConta(){
        return getOptionalAttr(Id::getConta).orElse(null);
    }

    @JsonbProperty
    public Periodo getPeriodo(){
        return id == null ? null : id.getPeriodo();
    }

    private <T> Optional<? extends T> getOptionalAttr(
            final Function<? super Id, ? extends T> getter) {
        return optionalId().map(getter);
    }

    private Optional<Id> optionalId() {
        return Optional.ofNullable(id);
    }

    @JsonbProperty("id")
    public String getSerialId() {
        if(this.serialId == null) {
            this.serialId=getId().getPeriodo().serialize();
        }
        return serialId;
    }

    @Override
    public String toString() {
        return "[Extrato:"+id+"|saldoAnt:"+saldoAnterior+"]";
    }
}
