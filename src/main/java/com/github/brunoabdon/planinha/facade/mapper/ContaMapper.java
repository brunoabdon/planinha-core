package com.github.brunoabdon.planinha.facade.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.github.brunoabdon.commons.facade.mappers.SimpleIdentifiableMapper;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.ContaVO;


@ApplicationScoped
public class ContaMapper
        implements SimpleIdentifiableMapper<Conta, ContaVO, Integer> {

    @Inject
    private Logger logger;

    @Override
    public ContaVO toVO(final Conta conta) {

        logger.logv(Level.DEBUG, "Mapeando pra VO {0}.", conta);

        if(conta == null) return null;

        return new ContaVO(conta.getId(),conta.getNome());
    }

}
