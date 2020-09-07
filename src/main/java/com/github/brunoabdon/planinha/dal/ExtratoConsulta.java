package com.github.brunoabdon.planinha.dal;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.dal.CriteriaUtils;
import com.github.brunoabdon.planinha.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.modelo.Periodo;

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
			 .filter(Objects::nonNull)
			 .findAny()
			 .orElse(0)
			 .intValue();
	}
	public List<ItemDeExtrato> itensDoExtrato(Conta conta, Periodo periodo) {
		return
			em.createNamedQuery(
				"Lancamento.itensDeUmExtrato",
				ItemDeExtrato.class
			).setParameter("conta", conta)
			 .setParameter("dataInicio", periodo.getDataMinima())
			 .setParameter("dataFim", periodo.getDataMaxima())
			 .getResultList();
	}


    public LocalDate pegaDiaInauguracaoDaConta(final Conta conta) {

        final LocalDate diaInauguracao;
        final List<LocalDate> resultList =
            em.createNamedQuery(
        		"Lancamento.menorDiaComFatoPraConta",
        		LocalDate.class
    		).setParameter("conta", conta)
             .getResultList();

        if(resultList == null) {
            //nunca foi estreiada
            diaInauguracao = null;
        } else {
            final int quantos = resultList.size();

            //reality check
            assert(quantos != 1);

            diaInauguracao = resultList.get(0);
        }

        return diaInauguracao;
    }
}
