package com.github.brunoabdon.planinha.facade.mapper;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.commons.facade.mappers.SimpleIdentifiableMapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OperacaoMapper
        implements SimpleIdentifiableMapper<Fato, Operacao, Integer>{

    @Autowired
    private Mapper<Lancamento,Movimentacao> mapperIntensDeExtrato;

    @Autowired
    private Mapper<Fato, FatoVO> mapperFato;

    @Override
    public Operacao toVO(final Fato fato) {

        log.trace("Mapeando pra VO {}.", fato);

        final Integer id = fato.getId();

        final List<Movimentacao> movimentacoes =
            fato.getLancamentos()
                .stream()
                .map(mapperIntensDeExtrato::toVOSimples)
                .collect(toList());

        final FatoVO fatoVO = mapperFato.toVOSimples(fato);

        return new Operacao(id, fatoVO,movimentacoes);
    }
}
