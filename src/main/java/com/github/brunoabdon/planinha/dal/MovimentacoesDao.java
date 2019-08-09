package com.github.brunoabdon.planinha.dal;

import javax.enterprise.context.ApplicationScoped;

import com.github.brunoabdon.commons.dal.AbstractDao;
import com.github.brunoabdon.planinha.Movimentacao;

@ApplicationScoped
public class MovimentacoesDao extends
        AbstractDao<Movimentacao, Integer> {

    public MovimentacoesDao() {
        super(Movimentacao.class);
    }

}
