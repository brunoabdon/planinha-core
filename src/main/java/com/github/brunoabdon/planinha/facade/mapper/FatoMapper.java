package com.github.brunoabdon.planinha.facade.mapper;

import javax.enterprise.context.ApplicationScoped;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.modelo.FatoVO;

@ApplicationScoped
public class FatoMapper implements Mapper<Fato, FatoVO> {

    @Override
    public FatoVO toVO(final Fato fato) {
        return new FatoVO(fato.getDia(), fato.getDescricao());
    }
}
