package com.github.brunoabdon.planinha.dal.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.brunoabdon.commons.modelo.Identifiable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A manifestação de um {@link #getValor() valor} numa {@link Conta} decorrente
 * de um {@link Fato}.
 *
 * @author bruno
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(schema = "planinhacore")
public class Lancamento implements Identifiable<Lancamento.Id>, Serializable {

    private static final long serialVersionUID = -3510137276546152596L;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Id implements Serializable {

        private static final long serialVersionUID = -1734494257092861534L;

        @Column(updatable = false, name = "fato_id")
        private Integer fatoId;

        @Column(updatable = false, name = "conta_id")
        private Integer contaId;

        @Override
            public String toString() {
                return "[IdLanc|" + contaId + fatoId + "]";
            }
    }

    @EmbeddedId
    @EqualsAndHashCode.Include
    private Id id;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(insertable = false, updatable = false, name = "fato_id")
    private Fato fato;

    @EqualsAndHashCode.Exclude
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false, name = "conta_id")
    private Conta conta;

    @EqualsAndHashCode.Exclude
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
    public String toString() {
        return "[Lancamento|" + id + "|val:" + valor + "]";
    }

}