package com.github.brunoabdon.planinha.dal;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.github.brunoabdon.planinha.modelo.Conta;

@ApplicationScoped
public class FatoConsulta {

    
    private static final String QUERYNAME_INAUGURACAO_CONTA =
		"Fato.menorDiaComFatoPraConta";

    @Inject
    Logger logger;
    
    @PersistenceContext
    EntityManager em;

    
    public LocalDate pegaDiaInauguracaoDaConta(final Conta conta) {
        
        final LocalDate diaInauguracao;
        final List<LocalDate> resultList = 
            em.createNamedQuery(QUERYNAME_INAUGURACAO_CONTA,LocalDate.class)
              .setParameter("conta", conta)
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
