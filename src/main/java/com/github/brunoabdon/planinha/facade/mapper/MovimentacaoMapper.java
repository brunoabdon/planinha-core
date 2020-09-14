package com.github.brunoabdon.planinha.facade.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.brunoabdon.commons.facade.mappers.IdMappingException;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.commons.facade.mappers.MappingException;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Operacao;

@ApplicationScoped
public class MovimentacaoMapper
        implements IdentifiableMapper<
                    Lancamento,
                    Lancamento.Id,
                    Movimentacao,
                    Movimentacao.Id
                > {

    private static final String ERR_MSG_ID_MAPPING = "Erro ao mapear id";

    @Inject
    IdentifiableMapper<Fato, Integer, Operacao , Integer> mapperOperacao;

    @Inject
    IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapperConta;

    @Override
    public Movimentacao toVO(final Lancamento lancamento) {

        final Integer idOperacao = extractIdOperacao(lancamento);

        final ContaVO conta = mapperConta.toVO(lancamento.getConta());

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,conta);

        return new Movimentacao(id,lancamento.getValor());
    }

    private Integer extractIdOperacao(final Lancamento lancamento) {
        final Integer idOperacao;

        try {
            idOperacao = mapperOperacao.toKey(lancamento.getFato().getId());
        } catch (final IdMappingException e) {
            throw new MappingException(
                e, Lancamento.class,Movimentacao.class,ERR_MSG_ID_MAPPING
            );
        }
        return idOperacao;
    }

    @Override
    public Lancamento.Id toKey(final Movimentacao.Id id) throws IdMappingException {

        return
            new Lancamento.Id(
                mapperOperacao.toKey(id.getOperacaoId()),
                mapperConta.toKey(id.getConta().getId())
            );
    }
}
