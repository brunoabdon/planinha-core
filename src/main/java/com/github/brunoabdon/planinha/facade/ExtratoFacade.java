package com.github.brunoabdon.planinha.facade;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.Level.DEBUG;

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
import com.github.brunoabdon.planinha.dal.ExtratoConsulta;
import com.github.brunoabdon.planinha.dal.FatoConsulta;
import com.github.brunoabdon.planinha.dal.ItemConsulta;
import com.github.brunoabdon.planinha.filtro.FiltroItem;
import com.github.brunoabdon.planinha.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.modelo.Extrato.Item;
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
    FatoConsulta fatoConsulta;
    
    @Inject
    ExtratoConsulta extratoConsulta;
    
    @Inject
    TimeUtils tmu;
        
    @Inject 
    ContaFacade contaFacade;
    
    @Inject 
    ItemConsulta itemConsulta;
    
    @Override
    public Extrato cria(final Extrato elemento) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Extrato pega(final Id key) throws EntidadeInexistenteException {

    	logger.logv(DEBUG, "Pegando extrato de id {0}.", key);
    	
		final Conta conta = contaFacade.pega(key.getConta().getId());
		final Periodo periodo = key.getPeriodo();
		final LocalDate dataInicial = periodo.getDataMinima();
		
		final int saldoAnterior = 
			extratoConsulta.saldoNoInicioDoDia(conta, dataInicial);
		
		final FiltroItem filtroItem = new FiltroItem();
		
		filtroItem.setConta(conta.getId());
		
		filtroItem.setDataMinima(periodo.getDataMinima());
		filtroItem.setDataMaxima(periodo.getDataMaxima());
		
		final List<Item> itens = itemConsulta.listar(filtroItem);
		
		return new Extrato(new Id(conta, periodo), saldoAnterior, itens);
    }

    @Override
    public List<Extrato> listar(final Integer idConta) {

        List<Extrato> extratos;
        
        Conta conta;
        try {
            conta = contaFacade.pega(idConta);
            extratos = lista(conta);
        } catch (final EntidadeInexistenteException e) {
            extratos = emptyList();
        }
        
        return extratos;
    }


    private List<Extrato> lista(final Conta conta) {
        final List<Extrato> extratos;

        final LocalDate diaInauguracaoDaConta = 
            fatoConsulta.pegaDiaInauguracaoDaConta(conta);

        if(diaInauguracaoDaConta == null) {
            extratos = emptyList();
        } else {
            
            extratos = 
                tmu.streamMensalAteHoje(diaInauguracaoDaConta)
                   .map(Periodo::mesDoDia)
                   .map(p -> new Extrato.Id(conta, p))
                   .map(Extrato::new)
                   .collect(toList());
        }
            
        return extratos;
    }

    @Override
    public Extrato atualiza(final Id key, final Void atualizacao) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleta(Id key) 
            throws EntidadeInexistenteException, BusinessException {
        throw new UnsupportedOperationException();
    }

}
