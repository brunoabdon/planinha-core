package com.github.brunoabdon.planinha.dal.modelo.virtual;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Subselect;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Entidade virtual que representa a abertura de una dada {@link Conta}. Tem
 * como propriedade o dia em que a conta foi 'aberta': será o dia em que
 * acontecer o {@link Fato} mais antigo com {@link Lancamento} para a conta em
 * questão.
 * .
 * @author bruno
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Entity
@Subselect(
    "  select "
    + " l.conta.id conta_id, min(l.fato.dia) dia "
    + "from "
    + " Lancamento l "
    + "group by "
    + " l.conta.id, l.fato.dia"
)
public class AberturaDeConta extends EntidadeBaseInt implements Serializable {

    private static final long serialVersionUID = -1395595938556997349L;

    @OneToOne
    @PrimaryKeyJoinColumn
    @EqualsAndHashCode.Exclude
    private Conta conta;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDate dia;


}
