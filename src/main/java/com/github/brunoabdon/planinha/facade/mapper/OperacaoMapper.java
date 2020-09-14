package com.github.brunoabdon.planinha.facade.mapper;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.commons.facade.mappers.SimpleIdentifiableMapper;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;

@ApplicationScoped
public class OperacaoMapper
        implements SimpleIdentifiableMapper<Fato, Operacao, Integer>{

    @Inject
    Mapper<Lancamento,Movimentacao> mapperIntensDeExtrato;

    @Inject
    Mapper<Fato, FatoVO> mapperFato;

    @Override
    public Operacao toVO(final Fato fato) {

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
