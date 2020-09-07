package com.github.brunoabdon.planinha.dal;

import static com.github.brunoabdon.planinha.modelo.Fato_.dia;
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
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.modelo.Periodo;

@ApplicationScoped
public class FatoConsultaFiltro {

    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    CriteriaUtils critUtils;

    public List<Fato> listar(final Periodo periodo){

    	logger.logv(DEBUG, "Listando fatos por {0}.", periodo);

    	final CriteriaBuilder cb = em.getCriteriaBuilder();

    	CriteriaQuery<Fato> cq = cb.createQuery(Fato.class);

    	final Root<Fato> rootFato = cq.from(Fato.class);

    	final Optional<Predicate> where =
			critUtils.between(
				cb,
				rootFato.get(dia),
				periodo.getDataMinima(),
				periodo.getDataMaxima()
			);

    	where.ifPresent(cq::where);

    	return em.createQuery(cq).getResultList();
    }
}
