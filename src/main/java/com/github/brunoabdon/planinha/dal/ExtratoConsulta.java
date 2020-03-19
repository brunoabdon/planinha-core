package com.github.brunoabdon.planinha.dal;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.dal.CriteriaUtils;
import com.github.brunoabdon.planinha.modelo.Conta;

@ApplicationScoped
public class ExtratoConsulta {


	@Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    CriteriaUtils critUtils;
	public int saldoNoInicioDoDia(final Conta conta, final LocalDate dia) {

		return
			em.createNamedQuery(
				"Lancamento.saldoDaContaNoInicioDoDia",
				Number.class
			).setParameter("conta", conta)
			 .setParameter("dia", dia)
			 .getResultStream()
			 .findAny()
			 .orElse(0)
			 .intValue();
	}
}
