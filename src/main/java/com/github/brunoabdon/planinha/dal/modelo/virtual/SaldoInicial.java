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
import javax.persistence.MapsId;

import org.hibernate.annotations.Subselect;

import com.github.brunoabdon.commons.modelo.Entidade;
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
 *
 * @author bruno
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect(
      " select c.id id, f.dia dia, sum(l_ant.valor) valor "
    + " from planinhacore.conta c "
    + " join planinhacore.lancamento l "
    + " on l.conta_id = c.id "
    + " join planinhacore.fato f "
    + " on l.fato_id = f.id "
    + " left join planinhacore.lancamento l_ant on l_ant.conta_id = c.id "
    + " left join planinhacore.fato f_ant on f_ant.id = l_ant.fato_id "
    + " where f_ant.dia < f.dia "
    + " group by c.id, f.dia "
)
public class SaldoInicial
    implements Entidade<SaldoInicial.Id>, Serializable {

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

        @Column(updatable = false, name = "id")
        private Integer contaId;

        @Column(nullable = false)
        private LocalDate dia;
    }

    @EmbeddedId
    @EqualsAndHashCode.Include
    private Id id;

    @MapsId("id")
    @JoinColumn(name="id")
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    private Conta conta;

    @EqualsAndHashCode.Exclude
    @Column(precision = 11, scale = 0, nullable = false)
    private int valor;
}
