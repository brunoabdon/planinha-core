package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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
			  + "where l.conta = :conta and l.operacao.fato.dia < :dia"
	),
@NamedQuery(
	name = "Lancamento.itensDeUmExtrato",
	query =
		"select "
		+ "new com.github.brunoabdon.planinha.modelo"
		+       ".ItemDeExtrato(l.operacao.fato, l.valor) "
		+ "from Lancamento l where "
		+ "l.operacao.fato.dia between :dataInicio and :dataFim "
		+ "and l.conta = :conta"
),
@NamedQuery(
	    name="Lancamento.menorDiaComFatoPraConta",
	    query="select min(l.operacao.fato.dia) from Lancamento l where l.conta = :conta"
	),

})
public class Lancamento implements Identifiable<Lancamento.Id>, Serializable{

    private static final long serialVersionUID = -3510137276546152596L;

    @Embeddable
    public static class Id implements Serializable {

		private static final long serialVersionUID = -1734494257092861534L;

		@Column(name="fato_id")
		private Integer operacaoId;

		@Column(name="conta_id")
    	private Integer contaId;

		public Id() {
		}

		public Id(final Integer operacaoId, final Integer contaId) {
			super();
			this.operacaoId = operacaoId;
			this.contaId = contaId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(contaId, operacaoId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			final Id other = (Id) obj;
			return
				Objects.equals(contaId, other.contaId)
				&& Objects.equals(operacaoId, other.operacaoId);
		}
    }

    @EmbeddedId
    @JsonbTransient
    private Id id;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false,name = "fato_id")
    private Operacao operacao;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Conta conta;

    @Column(precision=11, scale=0, nullable = false)
    private int valor;

    public Lancamento() {
        super();
    }

    @JsonbCreator
    public Lancamento(
            @JsonbProperty("conta") final Conta conta,
            @JsonbProperty("valor") final int valor) {
        this();
        this.conta = conta;
        this.valor = valor;
    }

	@Override
	public Id getId() {
		return id;
	}

    public Conta getConta() {
        return conta;
    }

    public int getValor() {
        return valor;
    }

    public void setId(final Id id) {
		this.id = id;
		if(id == null) {
			this.operacao = null;
			this.conta = null;
		} else {
			this.operacao = new Operacao(id.operacaoId);
			this.conta = new Conta(id.contaId);
		}
	}

    public void setValor(final int valor) {
        this.valor = valor;
    }

    public Operacao getOperacao() {
		return operacao;
	}

    public void setOperacao(final Operacao operacao) {
        this.operacao = operacao;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean equal = obj instanceof Lancamento;
        if(equal){
            final Lancamento lancamento = (Lancamento) obj;
            equal =
                Objects.equals(this.getId(),lancamento.getId())
                && Objects.equals(this.getValor(),lancamento.getValor())
                && Objects.equals(this.getConta(),lancamento.getConta())
                && Objects.equals(this.getOperacao(),lancamento.getOperacao());
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 11)
            .append(getId())
            .append(getValor())
            .append(getOperacao())
            .append(getConta())
            .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[lanc:");
        appendIdNotNull(sb, "id",id);
        appendIdNotNull(sb, "conta",conta);
        appendIdNotNull(sb, "operacao",operacao);
        appendIdNotNull(sb, "valor",valor);

        return sb.append("]").toString();
    }

    private static void appendIdNotNull(
            final StringBuilder sb,
            final String desc,
            final Object o){
        if(o != null) sb.append("|").append(desc).append(":").append(o);
    }
}