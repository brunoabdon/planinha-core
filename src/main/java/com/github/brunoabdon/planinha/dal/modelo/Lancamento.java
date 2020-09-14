package com.github.brunoabdon.planinha.dal.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.Identifiable;

/**
 * A manifestação de um {@link #getValor() valor} numa {@link Conta} decorrente
 * de um {@link Fato}.
 *
 * @author bruno
 */
@Entity
@Table(schema = "planinhacore")
@NamedQueries({
    @NamedQuery(
        name = "Lancamento.saldoDaContaNoInicioDoDia",
        query = "select sum(valor) from Lancamento l "
                + "where l.conta = :conta and l.fato.dia < :dia"
    ),
    @NamedQuery(
        name = "Lancamento.lancamentosDaContaNoPeriodo",
        query =
            "select l from Lancamento l "
            + "where l.fato.dia between :dataInicio and :dataFim "
            + "and l.conta = :conta"
        ),
    @NamedQuery(
        name = "Lancamento.menorDiaComFatoPraConta",
        query = "select min(l.fato.dia) from Lancamento l "
                + "where l.conta = :conta"
    )
})
public class Lancamento implements Identifiable<Lancamento.Id>, Serializable {

    private static final long serialVersionUID = -3510137276546152596L;

    @Embeddable
    public static class Id implements Serializable {

        private static final long serialVersionUID = -1734494257092861534L;

        @Column(updatable = false, name = "fato_id")
        private Integer fatoId;

        @Column(updatable = false, name = "conta_id")
        private Integer contaId;

        public Id() {
        }

        public Id(final Integer fatoId, final Integer contaId) {
            this.contaId = contaId;
            this.fatoId = fatoId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(contaId, fatoId);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final Id other = (Id) obj;

            return Objects.equals(contaId, other.contaId)
                    && Objects.equals(fatoId, other.fatoId);
        }

        @Override
            public String toString() {
                return "[IdLanc|" + contaId + fatoId + "]";
            }
    }

    @EmbeddedId
    private Id id;

    @ManyToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false, name = "fato_id")
    private Fato fato;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false, name = "conta_id")
    private Conta conta;

    @Column(precision = 11, scale = 0, nullable = false)
    private int valor;

    public Lancamento() {
        super();
        this.id = new Id();
    }

    public Lancamento(final Fato fato, final Conta conta, final int valor) {
        this.id = new Lancamento.Id(fato.getId(), conta.getId());
        this.fato = fato;
        this.conta = conta;
        this.valor = valor;
    }

    @Override
    public Id getId() {
        return id;
    }

    public int getValor() {
        return valor;
    }

    public void setId(final Id id) {
        this.id = id;
    }

    public void setValor(final int valor) {
        this.valor = valor;
    }

    public Fato getFato() {
        return fato;
    }

    public Conta getConta() {
        return conta;
    }

    public void setFato(Fato fato) {
        this.fato = fato;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean equal = obj instanceof Lancamento;
        if (equal) {
            final Lancamento lancamento = (Lancamento) obj;
            equal = Objects.equals(this.getId(), lancamento.getId());
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[Lancamento|" + id + "|val:" + valor + "]";
    }

}