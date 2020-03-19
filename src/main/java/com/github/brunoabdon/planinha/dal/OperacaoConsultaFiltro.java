package com.github.brunoabdon.planinha.dal;

import static com.github.brunoabdon.planinha.modelo.Fato_.dia;
import static com.github.brunoabdon.planinha.modelo.Operacao_.fato;
import static org.jboss.logging.Logger.Level.DEBUG;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.dal.CriteriaUtils;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.modelo.Periodo;

@ApplicationScoped
public class OperacaoConsultaFiltro {

    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    CriteriaUtils critUtils;

    public List<Operacao> listar(final Periodo periodo){

    	logger.logv(DEBUG, "Listando operações por {0}.", periodo);

    	final CriteriaBuilder cb = em.getCriteriaBuilder();

    	CriteriaQuery<Operacao> cq = cb.createQuery(Operacao.class);

    	final Root<Operacao> rootOperacao = cq.from(Operacao.class);

    	final Optional<Predicate> where =
			critUtils.between(
				cb,
				rootOperacao.get(fato).get(dia),
				periodo.getDataMinima(),
				periodo.getDataMaxima()
			);

    	where.ifPresent(cq::where);

    	return em.createQuery(cq).getResultList();
    }
}
