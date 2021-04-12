package com.github.brunoabdon.planinha.facade.mapper;

import org.springframework.stereotype.Component;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.modelo.FatoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FatoMapper implements Mapper<Fato, FatoVO> {

    @Override
    public FatoVO toVO(final Fato fato) {

        log.trace("Mapeando pra VO {}.", fato);

        return new FatoVO(fato.getDia(), fato.getDescricao());
    }
}
