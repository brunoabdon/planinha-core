package com.github.brunoabdon.planinha.app;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ManagedBean
@ApplicationScoped
public class ApplicationProvieder {

    @PersistenceContext(unitName="planinhaPU")
    private EntityManager em;

    
    @Planinha
    public EntityManager getEntityManager() {
    	return this.em;
    }
	
}
