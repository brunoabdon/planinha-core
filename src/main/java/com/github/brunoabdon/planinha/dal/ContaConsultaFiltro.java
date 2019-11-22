package com.github.brunoabdon.planinha.dal;

import static com.github.brunoabdon.planinha.modelo.Conta_.nome;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.brunoabdon.planinha.modelo.Conta;

@ApplicationScoped
public class ContaConsultaFiltro {
    
    @PersistenceContext
    EntityManager em;
    
    public List<Conta> listar(final String parteDoNome){
        
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Conta> cq = cb.createQuery(Conta.class);
        final Root<Conta> root = cq.from(Conta.class);
        
        if(isNotBlank(parteDoNome)) {
            final Predicate nomeLikeTal = 
                cb.like(root.get(nome), "%" + parteDoNome + "%"); 
            cq.where(nomeLikeTal);
        }
        
        final TypedQuery<Conta> q = em.createQuery(cq);
        
        return q.getResultList();
    }
    
    public boolean estaEmUso(final Conta conta) {
        return  
            em
            .createNamedQuery("Conta.temLancamento", Boolean.class)
            .setParameter("conta", conta)
            .getSingleResult();
    }
    
    
}
