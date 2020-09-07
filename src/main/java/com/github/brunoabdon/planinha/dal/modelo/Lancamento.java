package com.github.brunoabdon.planinha.dal.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    	name = "Lancamento.itensDeUmExtrato",
    	query =
    		"select "
    		+ "new com.github.brunoabdon.planinha.modelo"
    		+       ".ItemDeExtrato(l.operacao.fato, l.valor) "
    		+ "from Lancamento l "
    		+ "where l.fato.dia between :dataInicio and :dataFim "
    		+ "and l.conta = :conta"
    ),
    @NamedQuery(
        name="Lancamento.menorDiaComFatoPraConta",
        query=
            "select min(l.operacao.fato.dia) from Lancamento l "
            + "where l.conta = :conta"
    )
})
public class Lancamento implements Identifiable<Lancamento.Id>, Serializable{

    private static final long serialVersionUID = -3510137276546152596L;

    @Embeddable
    public static class Id implements Serializable {

		private static final long serialVersionUID = -1734494257092861534L;

		@Column(name="fato_id")
		private Integer fatoId;

		@Column(name="conta_id")
    	private Integer contaId;

		public Id() {
		}

		public Id(final Integer fatoId, final Integer contaId) {
			super();
			this.fatoId = fatoId;
			this.contaId = contaId;
		}

		public Integer getFatoId() {
            return fatoId;
        }

		public Integer getContaId() {
            return contaId;
        }

		@Override
		public int hashCode() {
			return Objects.hash(contaId, fatoId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			final Id other = (Id) obj;
			return
				Objects.equals(contaId, other.contaId)
				&& Objects.equals(fatoId, other.fatoId);
		}
    }

    @EmbeddedId
    private Id id;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false,name = "fato_id")
    private Fato fato;

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

    public Lancamento(
            final Id id,
            final int valor) {
        this();
        this.id = id;
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
			this.fato = null;
			this.conta = null;
		} else {
			this.fato = new Fato(id.fatoId);
			this.conta = new Conta(id.contaId);
		}
	}

    public void withFato(final Integer fatoId) {
        this.setId(
            new Lancamento.Id(
                fatoId,
                getConta().getId()
            )
        );
    }

    public void setValor(final int valor) {
        this.valor = valor;
    }

    public Fato getFato() {
        return fato;
    }

    public void setFato(final Fato fato) {
        this.fato = fato;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean equal = obj instanceof Lancamento;
        if(equal){
            final Lancamento lancamento = (Lancamento) obj;
            equal = Objects.equals(this.getId(),lancamento.getId());
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[lanc:");
        appendIdNotNull(sb, "id",id);
        appendIdNotNull(sb, "conta",conta);
        appendIdNotNull(sb, "fato",fato);
        return sb.append("|valor").append(valor).append("]").toString();
    }

    private static void appendIdNotNull(
            final StringBuilder sb,
            final String desc,
            final Object o){
        if(o != null) sb.append("|").append(desc).append(":").append(o);
    }
}