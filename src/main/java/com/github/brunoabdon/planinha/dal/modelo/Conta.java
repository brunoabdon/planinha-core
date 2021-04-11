package com.github.brunoabdon.planinha.dal.modelo;

import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;


/**
 * Uma carteira à qual se pode associar e movimentar um valor.
 *
 * @author bruno
 */
@Entity
@Table(schema = "planinhacore")
public class Conta extends EntidadeBaseInt {

    private static final long serialVersionUID = 7321886996603362113L;

    public static final int NOME_MAX_LEN = 50;

    @Size(max = NOME_MAX_LEN)
    @Column(length = NOME_MAX_LEN, nullable = false, unique = true)
    private String nome;

	@OneToMany(fetch = LAZY, mappedBy = "conta")
	private List<Lancamento> movimentacoes;

    public Conta() {
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public static Conta fromString(final String str){
        return EntidadeBaseInt.fromString(Conta.class, str);
    }

    @Override
    public String toString() {
        return "[Conta:"+getId()+"|" + nome + "]";
    }
}