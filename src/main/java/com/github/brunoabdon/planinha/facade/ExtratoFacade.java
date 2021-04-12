package com.github.brunoabdon.planinha.facade;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.planinha.dal.AberturaDeContaRepository;
import com.github.brunoabdon.planinha.dal.ContaRepository;
import com.github.brunoabdon.planinha.dal.LancamentoRepository;
import com.github.brunoabdon.planinha.dal.SaldoInicialRepository;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.dal.modelo.virtual.AberturaDeConta;
import com.github.brunoabdon.planinha.dal.modelo.virtual.SaldoInicial;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.util.TimeUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Setter(PACKAGE)
public class ExtratoFacade
        implements Facade<Extrato, Extrato.Id, Integer, Void> {

    @Autowired
    private IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapperConta;

    @Autowired
    private Mapper<Lancamento,ItemDeExtrato> mapperItemDeExtrato;

    @Autowired
    private LancamentoRepository lancamentoRepo;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private SaldoInicialRepository  saldoInicialRepo;

    @Autowired
    private AberturaDeContaRepository aberturaDeContaRepo;

    @Autowired
    private TimeUtils timeUtils;

    //TODO usar um bean @autowired
    private Clock clock = Clock.systemDefaultZone();

    @Override
    public Extrato cria(final Extrato extrato) throws BusinessException {
    	log.warn("Extrato se conquista.");
        throw new UnsupportedOperationException();
    }

    @Override
    public Extrato pega(final Id key) throws EntidadeInexistenteException {

    	log.debug( "Pegando extrato de id {}.", key);

		final Conta conta = pegaContaDoExtrato(key);
		final Integer idConta = conta.getId();

		final Periodo periodo = key.getPeriodo();
		final LocalDate dataInicial = periodo.getInicio();

		final int saldoAnterior =
	        saldoInicialRepo
	            .findById(new SaldoInicial.Id(idConta, dataInicial))
	            .map(SaldoInicial::getValor)
	            .orElse(0);


		final List<ItemDeExtrato> itens =
			lancamentoRepo
			    .findByContaPeriodo(conta, periodo)
			    .stream()
			    .map(mapperItemDeExtrato::toVO)
			    .collect(toList());

		final Extrato.Id id = new Extrato.Id(mapperConta.toVO(conta),periodo);

		return new Extrato(id, saldoAnterior, itens);
    }

    private Conta pegaContaDoExtrato(final Id id)
            throws EntidadeInexistenteException {

        log.debug("Pegando conta (Entity) do extrato de id {}.", id);

        final Integer idConta = mapperConta.toKey(id.getConta().getId());

        return
            contaRepository
                .findById(idConta)
                .orElseThrow(
                    () -> new EntidadeInexistenteException(Extrato.class,id)
                );
    }

    @Override
    public Page<Extrato> lista(final Integer idConta, final Pageable pageable) {

    	log.debug("Listando extratos da conta de id {}.", idConta);

        return
            contaRepository
                .findById(idConta)
                .map(c -> lista(c,pageable))
                .orElseGet(Page::empty);
    }

    private Page<Extrato> lista(final Conta conta, final Pageable pageable) {

        log.debug("Listando extratos da conta {}.", conta);

        return
            aberturaDeContaRepo
                .findByConta(conta)
                .map(abertura -> this.lista(abertura, pageable))
                .orElseGet(Page::empty);
    }

    private Page<Extrato> lista(
            final AberturaDeConta aberturaDeConta,
            final Pageable pageable){

        log.debug(
            "Listando extratos desde a abertura da conta {} ({}).",
            aberturaDeConta, pageable
        );

        final LocalDate diaInauguracaoDaConta = aberturaDeConta.getDia();

        final Conta conta = aberturaDeConta.getConta();
        final ContaVO contaVO = mapperConta.toVOSimples(conta);

        final Function<Periodo,Extrato.Id> toIdExtrato =
            p -> new Extrato.Id(contaVO, p);

        final LocalDate hoje = LocalDate.now(clock);

        final long quantosMesesAteHoje =
            timeUtils.quantosMesesEntre(diaInauguracaoDaConta, hoje);

        log.trace(
            "Hoje {} já passaram {} meses de {}, quando foi inaugurada {}.",
            hoje, quantosMesesAteHoje, diaInauguracaoDaConta, conta
        );

        final List<Extrato> extratos =
            timeUtils
                .streamDeTantosMeses(diaInauguracaoDaConta,quantosMesesAteHoje)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(Periodo::mesDoDia)
                .map(toIdExtrato)
                .map(Extrato::new)
                .collect(Collectors.toList());

        return new PageImpl<>(extratos, pageable, quantosMesesAteHoje);
    }

    @Override
    public Extrato atualiza(final Id key, final Void atualizacao)
            throws BusinessException {
    	log.warn("Extrato não se atualiza.");
    	throw new UnsupportedOperationException();
    }

    @Override
    public void deleta(Id key) throws BusinessException {
        log.warn("Extrato não se deleta.");
        throw new UnsupportedOperationException();
    }
}
