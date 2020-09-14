package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;

import com.github.brunoabdon.commons.modelo.Identifiable;

public class Movimentacao
        implements Identifiable<Movimentacao.Id>, Serializable {

    private static final long serialVersionUID = 6746391695579473165L;

    public static class Id implements Serializable {

        private static final long serialVersionUID = -6750023797871359900L;

        private Integer operacaoId;

        private ContaVO conta;

        private Id() {
        }

        public Id(final Integer operacaoId, final ContaVO conta) {
            this();
            this.operacaoId = operacaoId;
            this.conta = conta;
        }

        public Id(final Integer operacaoId, final Integer idConta) {
            this();
            this.operacaoId = operacaoId;
            this.conta = new ContaVO(idConta);
        }

        public ContaVO getConta() {
            return conta;
        }

        public Integer getOperacaoId() {
            return operacaoId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operacaoId, conta);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            final Id other = (Id) obj;

            return
                Objects.equals(conta, other.conta)
                && Objects.equals(operacaoId, other.operacaoId);
        }

        @Override
        public String toString() {
            return "[MovId|" + operacaoId + "/" + conta + "]";
        }
    }

    @JsonbTransient
    private final Id id;

    private int valor;

    public Movimentacao() {
        this.id = new Id();
    }

    public Movimentacao(final Id id, int valor) {
        this.id = id;
        this.valor = valor;
    }

    public ContaVO getConta() {
        return id.conta;
    }

    public void setConta(final ContaVO conta) {
        this.id.conta = conta;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(final int valor) {
        this.valor = valor;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        return Objects.equals(id, ((Movimentacao) obj).id);
    }

    @Override
    public String toString() {
        return "[Mov:|" + id + "|$" +  valor + "]";
    }
}
