package com.github.brunoabdon.planinha.facade.mapper;

import org.springframework.stereotype.Component;

import com.github.brunoabdon.commons.facade.mappers.SimpleIdentifiableMapper;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.ContaVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContaMapper
        implements SimpleIdentifiableMapper<Conta, ContaVO, Integer> {

    @Override
    public ContaVO toVO(final Conta conta) {

        log.trace("Mapeando pra VO {}.", conta);

        if(conta == null) return null;

        return new ContaVO(conta.getId(),conta.getNome());
    }
}
