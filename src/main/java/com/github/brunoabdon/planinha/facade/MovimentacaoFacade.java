package com.github.brunoabdon.planinha.facade;

import static lombok.AccessLevel.PACKAGE;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.planinha.dal.ContaRepository;
import com.github.brunoabdon.planinha.dal.FatoRepository;
import com.github.brunoabdon.planinha.dal.LancamentoRepository;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Movimentacao.Id;
import com.github.brunoabdon.planinha.modelo.Operacao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(PACKAGE)
@Service
public class MovimentacaoFacade
        implements Facade<Movimentacao, Id, Integer, Integer> {

    @Autowired
    private OperacaoFacade operacaoFacade;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private FatoRepository fatoRepository;

    @Autowired
    private IdentifiableMapper<
        Lancamento,Lancamento.Id,Movimentacao,Movimentacao.Id
        > mapper;

    @Autowired
    private IdentifiableMapper<Fato, Integer, Operacao, Integer> mapperOperacao;

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public Movimentacao cria(@NotNull @Valid final Movimentacao movimentacao)
            throws BusinessException{

        log.debug("Criando movimentação {}.", movimentacao);

        final Fato fato = extraiFato(movimentacao);

        final Conta conta = extraiConta(movimentacao);

        final boolean fatoTemLancamentoDaConta =
            fato.getLancamentos()
                .stream()
                .map(Lancamento::getConta)
                .anyMatch(conta::equals);

        if(fatoTemLancamentoDaConta) {
            //TODO especificar o erro.. EntidadeInvalidaException, etc..
            log.warn(
                "Não adicionando mais um lancamento da conta {} no fato {}.",
                conta, fato
            );
            throw new BusinessException();
        }

        final Lancamento lancamento =
            new Lancamento(fato, conta, movimentacao.getValor());

        final Lancamento lancamentoCriado =
            lancamentoRepository.save(lancamento);

        return mapper.toVO(lancamentoCriado);
    }

    private Conta extraiConta(final Movimentacao movimentacao)
            throws BusinessException {

        final Integer idConta = movimentacao.getConta().getId();

        //TODO especializar. EntidadeInvalidaExceptio? ContaInvalida...
        return
            contaRepository
                .findById(idConta)
                .orElseThrow(BusinessException::new);
    }

    private Fato extraiFato(final Movimentacao movimentacao)
            throws EntidadeInexistenteException {

        final Integer idOperacao = movimentacao.getId().getOperacaoId();

        final Integer idFato = mapperOperacao.toKey(idOperacao);

        return
            fatoRepository
                .findById(idFato)
                .orElseThrow(
                    () -> new EntidadeInexistenteException(
                            Operacao.class,
                            idOperacao
                    )
                );
    }

    @Override
    public Movimentacao pega(@NotNull @Valid final Movimentacao.Id id)
            throws EntidadeInexistenteException {

        final Lancamento lancamento = pega2(id);

        return mapper.toVO(lancamento);
    }

    private Lancamento pega2(final Movimentacao.Id id)
            throws EntidadeInexistenteException {

        final Lancamento.Id idLancamento = mapper.toKey(id);

        return
            lancamentoRepository
                .findById(idLancamento)
                .orElseThrow(
                    () -> new EntidadeInexistenteException(
                        Movimentacao.class, id
                    )
                );
    }

    @Override
    public List<Movimentacao> lista(final Integer idOperacao)
            throws EntidadeInexistenteException {
        return operacaoFacade.pega(idOperacao).getMovimentacoes();
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public Movimentacao atualiza(
                @NotNull @Valid final Id id,
                @NotNull @Valid final Integer valor)
            throws BusinessException {

        final Lancamento lancamento = this.pegaLancamento(id);
        lancamento.setValor(valor);
        return mapper.toVO(lancamento);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public void deleta(@NotNull @Valid final Movimentacao.Id id)
            throws BusinessException {

        final Lancamento lancamento = pegaLancamento(id);

        final List<Lancamento> lancamentos =
            lancamento.getFato().getLancamentos();

        lancamentos.remove(lancamento);

        if(lancamentos.isEmpty()) {
            log.warn(
                "Impedindo a remoção de lancamento filho único {}.",
                lancamento
            );
            //TODO dar a real que a operação não pode ficar sem movimentacoes
            throw new BusinessException();
        }
    }

    private Lancamento pegaLancamento(final Movimentacao.Id id)
            throws EntidadeInexistenteException {

        final Lancamento.Id k = mapper.toKey(id);

        return
            lancamentoRepository
                .findById(k)
                .orElseThrow(
                    () -> new EntidadeInexistenteException(
                        Movimentacao.class, id
                    )
                );
    }

}
