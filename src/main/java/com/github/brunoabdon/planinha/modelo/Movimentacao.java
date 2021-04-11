package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.brunoabdon.commons.modelo.Identifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Movimentacao
        implements Identifiable<Movimentacao.Id>, Serializable {

    private static final long serialVersionUID = 6746391695579473165L;

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {

        private static final long serialVersionUID = -6750023797871359900L;

        private Integer operacaoId;

        private ContaVO conta;

        public Id(final Integer operacaoId, final Integer idConta) {
            this(operacaoId, new ContaVO(idConta));
        }

        @Override
        public String toString() {
            return "[MovId|" + operacaoId + "/" + conta + "]";
        }
    }

    @JsonIgnore
    @EqualsAndHashCode.Include
    private final Id id;

    @EqualsAndHashCode.Exclude
    private int valor;

    public Movimentacao() {
        this.id = new Id();
    }

    public ContaVO getConta() {
        return id.conta;
    }

    public void setConta(final ContaVO conta) {
        this.id.conta = conta;
    }

    @Override
    public String toString() {
        return "[Mov:|" + id + "|$" +  valor + "]";
    }
}
