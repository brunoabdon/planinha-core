package com.github.brunoabdon.planinha.dal.modelo.virtual;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Subselect;

import com.github.brunoabdon.commons.modelo.Identifiable;
import com.github.brunoabdon.planinha.dal.modelo.Conta;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidade virtual que representa o saldo de uma dada {@link Conta} no início
 * de um dado dia.
 * @author bruno
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Entity
@Subselect(
    "   select "
    + "   l.conta.id conta_id, "
    + "   l.fato.dia dia, "
    + "   sum(l.valor) valor "
    + " from Lancamento l "
    + " group by l.conta.id, l.fato.dia"
)
public class SaldoInicial
    implements Identifiable<SaldoInicial.Id>, Serializable {

    private static final long serialVersionUID = -151809088227593872L;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Id implements Serializable {

        private static final long serialVersionUID = -1734494257092861534L;

        @Column(updatable = false, name = "conta_id")
        private Integer contaId;

        @Column(nullable = false)
        private LocalDate dia;
    }

    @EmbeddedId
    private Id id;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false, name = "conta_id")
    private Conta conta;

    @Column(precision = 11, scale = 0, nullable = false)
    private int valor;

}
