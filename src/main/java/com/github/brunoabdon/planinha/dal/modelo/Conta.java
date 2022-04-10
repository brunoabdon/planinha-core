package com.github.brunoabdon.planinha.dal.modelo;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.github.brunoabdon.commons.modelo.EntidadeDeIdInteger;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Uma carteira à qual se pode associar e movimentar um valor.
 *
 * @author bruno
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "planinhacore")
public class Conta extends EntidadeDeIdInteger {

    private static final long serialVersionUID = 7321886996603362113L;

    public static final int NOME_MAX_LEN = 50;

    @EqualsAndHashCode.Exclude
    @Size(max = NOME_MAX_LEN)
    @Column(length = NOME_MAX_LEN, nullable = false, unique = true)
    private String nome;

    @EqualsAndHashCode.Exclude
	@OneToMany(fetch = LAZY, mappedBy = "conta")
	private List<Lancamento> movimentacoes;

    public Conta(final Integer id) {
        this(id,null);
    }

    public Conta(final String nome) {
        this.nome = nome;
    }

    public Conta(final Integer id, final String nome) {
        this(nome);
        super.setId(id);
    }

    @Override
    public String toString() {
        return "[Conta:"+getId()+"|" + nome + "]";
    }
}