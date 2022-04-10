package com.github.brunoabdon.planinha.dal.modelo.virtual;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.github.brunoabdon.commons.modelo.EntidadeDeIdInteger;
import org.hibernate.annotations.Subselect;

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
  "select c.id id, min(f.dia) dia "
  + " from planinhacore.lancamento l "
  + " join planinhacore.fato f on f.id = l.fato_id "
  + " join planinhacore.conta c on c.id = l.conta_id "
  + " group by c.id "
)
public class AberturaDeConta extends EntidadeDeIdInteger implements Serializable {

    private static final long serialVersionUID = -1395595938556997349L;

    @OneToOne
    @PrimaryKeyJoinColumn
    @EqualsAndHashCode.Exclude
    private Conta conta;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDate dia;
}
