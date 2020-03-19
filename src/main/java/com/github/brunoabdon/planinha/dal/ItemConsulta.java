package com.github.brunoabdon.planinha.dal;

import static com.github.brunoabdon.planinha.modelo.Fato_.dia;
import static com.github.brunoabdon.planinha.modelo.Lancamento_.conta;
import static com.github.brunoabdon.planinha.modelo.Lancamento_.fato;
import static com.github.brunoabdon.planinha.modelo.Lancamento_.valor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.dal.CriteriaUtils;
import com.github.brunoabdon.planinha.filtro.FiltroItem;
import com.github.brunoabdon.planinha.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.Conta_;
import com.github.brunoabdon.planinha.modelo.Extrato.Item;
import com.github.brunoabdon.planinha.modelo.Fato;
import com.github.brunoabdon.planinha.modelo.Fato_;
import com.github.brunoabdon.planinha.modelo.Lancamento;

@ApplicationScoped
public class ItemConsulta {

    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    CriteriaUtils critUtils;

    public List<Item> listar(final FiltroItem filtro){

    	final CriteriaBuilder cb = em.getCriteriaBuilder();

    	CriteriaQuery<Item> cq = cb.createQuery(Item.class);

    	final Root<Lancamento> rootLancamento = cq.from(Lancamento.class);
    	final Join<Lancamento, Fato> joinFato = rootLancamento.join(fato);
    	final Join<Lancamento, Conta> joinConta =
			rootLancamento.join(conta);

    	final Path<Integer> colContaId = joinConta.get(Conta_.id);

    	final Path<Integer> colFatoId = joinFato.get(Fato_.id);
		final Path<LocalDate> colDia = joinFato.get(dia);
		final Path<String> colDescricao = joinFato.get(Fato_.descricao);

		final Path<Integer> colValor = rootLancamento.get(valor);

    	final List<Predicate> where = new ArrayList<>();

		critUtils.eq(cb,colContaId,filtro.getConta())
    	         .ifPresent(where::add);

		critUtils.greaterThanOrEqualTo(cb, colDia,filtro.getDataMinima())
				 .ifPresent(where::add);

		critUtils.lessThanOrEqualTo(cb, colDia,filtro.getDataMaxima())
		         .ifPresent(where::add);

		final CompoundSelection<Item> constrItem =
			cb.construct(Item.class, colFatoId, colDia, colDescricao, colValor );

		cq = cq.select(constrItem);

		cq = cq.where(where.toArray(new Predicate[where.size()]));

		cq.orderBy(cb.asc(colDia));

		final TypedQuery<Item> q = em.createQuery(cq);

    	return q.getResultList();
    }
}
