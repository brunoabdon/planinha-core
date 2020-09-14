package com.github.brunoabdon.planinha.facade;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.WARN;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.commons.facade.mappers.Mapper;
import com.github.brunoabdon.planinha.dal.ExtratoConsulta;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.modelo.ItemDeExtrato;
import com.github.brunoabdon.planinha.modelo.Periodo;
import com.github.brunoabdon.planinha.util.TimeUtils;

@ApplicationScoped
public class ExtratoFacade
        implements Facade<Extrato, Extrato.Id, Integer, Void> {

	@Inject
	Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapperConta;

    @Inject
    Mapper<Lancamento,ItemDeExtrato> mapperItemDeExtrato;

    @Inject
    ExtratoConsulta extratoConsulta;

    @Inject
    TimeUtils tmu;

    @Override
    public Extrato cria(final Extrato extrato) throws BusinessException {
    	logger.log(WARN, "Extrato se conquista.");
        throw new UnsupportedOperationException();
    }

    @Override
    public Extrato pega(final Id key) throws EntidadeInexistenteException {

    	logger.logv(DEBUG, "Pegando extrato de id {0}.", key);

		final Conta conta = pegaContaDoExtrato(key);

		final Periodo periodo = key.getPeriodo();
		final LocalDate dataInicial = periodo.getDataMinima();

		final int saldoAnterior =
			extratoConsulta.saldoNoInicioDoDia(conta, dataInicial);

		final List<ItemDeExtrato> itens =
			extratoConsulta
			    .lancamentosDoExtrato(conta,periodo)
			    .stream()
			    .map(mapperItemDeExtrato::toVO)
			    .collect(toList());

		final Extrato.Id id = new Extrato.Id(mapperConta.toVO(conta),periodo);

		return new Extrato(id, saldoAnterior, itens);
    }

    private Conta pegaContaDoExtrato(final Id id)
            throws EntidadeInexistenteException {

        logger.logv(DEBUG, "Pegando conta (Entity) do extrato de id {0}.", id);

        final Integer idConta = mapperConta.toKey(id.getConta().getId());

        final Conta conta = em.find(Conta.class, idConta);

        if(conta == null)
            throw new EntidadeInexistenteException(Extrato.class, id);

        return conta;
    }

    @Override
    public List<Extrato> lista(final Integer idConta) {

    	logger.logv(DEBUG, "Listando extratos da conta de id {0}.", idConta);

        List<Extrato> extratos;

        final Conta conta = em.find(Conta.class, idConta);
        if(conta == null) {
            logger.logv(WARN,"Zero extratos da conta inexistente {0}.",idConta);
            extratos = emptyList();
        } else {
            extratos = lista(conta);
        }

        return extratos;
    }

    private List<Extrato> lista(final Conta conta) {

        logger.logv(DEBUG, "Listando extratos da conta {0}.", conta);

        final List<Extrato> extratos;

        final LocalDate diaInauguracaoDaConta =
    		extratoConsulta.pegaDiaInauguracaoDaConta(conta);

        if(diaInauguracaoDaConta == null) {
            logger.logv(DEBUG, "Extrato vazio pra conta virgem {0}.", conta);
            extratos = emptyList();
        } else {

            logger.logv(
                DEBUG, "Listando extrados desde {0} pra conta {1}.",
                diaInauguracaoDaConta, conta
            );

            final ContaVO contaVO = mapperConta.toVOSimples(conta);

            extratos =
                tmu.streamMensalAteHoje(diaInauguracaoDaConta)
                   .map(Periodo::mesDoDia)
                   .map(p -> new Extrato.Id(contaVO, p))
                   .map(Extrato::new)
                   .collect(toList());
        }

        return extratos;
    }

    @Override
    public Extrato atualiza(final Id key, final Void atualizacao)
            throws EntidadeInexistenteException, BusinessException {
    	logger.log(WARN, "Extrato não se atualiza.");
    	throw new UnsupportedOperationException();
    }

    @Override
    public void deleta(Id key)
            throws EntidadeInexistenteException, BusinessException {
    	logger.log(WARN, "Extrato não se deleta.");
        throw new UnsupportedOperationException();
    }
}
