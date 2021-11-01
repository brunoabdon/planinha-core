package com.github.brunoabdon.planinha.modelo;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.brunoabdon.commons.modelo.Identifiable;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.commons.modelo.conv.PeriodoConverter;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class Extrato implements Identifiable<Id>, Serializable{

    private static final long serialVersionUID = 3530634103009951958L;

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Id implements Serializable{

        private static final long serialVersionUID = 3587349119460038562L;

        private ContaVO conta;
        private Periodo periodo;

        @Override
        public String toString() {
        	return "["+conta+"|"+periodo+"]";
        }
    }

    @JsonIgnore
    @Getter
    @EqualsAndHashCode.Include
    private Id id;

    @EqualsAndHashCode.Exclude
    private String serialId;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private Number saldoAnterior;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private List<ItemDeExtrato> items;

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

    public ContaVO getConta(){
        return getOptionalAttr(Id::getConta).orElse(null);
    }

    @JsonProperty
    public Periodo getPeriodo(){
        return getOptionalAttr(Id::getPeriodo).orElse(null);
    }

    private <T> Optional<? extends T> getOptionalAttr(
            final Function<? super Id, ? extends T> getter) {
        return optionalId().map(getter);
    }

    private Optional<Id> optionalId() {
        return Optional.ofNullable(id);
    }

    @JsonProperty("id")
    public String getSerialId() {
        if(this.serialId == null) {
            final Periodo periodo = getId().getPeriodo();
            this.serialId=
                PeriodoConverter.ToString.INSTANCE.convert(periodo);
        }
        return serialId;
    }

    @Override
    public String toString() {
        return "[Extrato:"+id+"|saldoAnt:"+saldoAnterior+"]";
    }
}
