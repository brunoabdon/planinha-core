package com.github.brunoabdon.planinha.dal;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.persistence.EntityManager;

import com.github.brunoabdon.commons.dal.DalException;
import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.util.modelo.Periodo;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.gastoso.dal.LancamentosDao;
import com.github.brunoabdon.gastoso.system.FiltroContas;
import com.github.brunoabdon.gastoso.system.FiltroFatos;
import com.github.brunoabdon.gastoso.system.FiltroLancamentos;
import com.github.brunoabdon.planinha.Extrato;
import com.github.brunoabdon.planinha.Extrato.Id;
import com.github.brunoabdon.planinha.Extrato.Item;

public class ExtratosDao implements Dao<Extrato, Id> {

    private LancamentosDao lancamentosDao;
    
    public ExtratosDao() {
        this.lancamentosDao = new LancamentosDao();
    }
    
    @Override
    public Extrato find(final EntityManager em, final Id id) 
            throws DalException {
        
        final  Conta conta = id.getConta();
        final Periodo periodo = id.getPeriodo();

        final Object result =
    		em.createNamedQuery("saldoDaContaNoInicioDoDia")
    		.setParameter("conta", conta)
    		.setParameter("dia", periodo.getDataMinima())
    		.getSingleResult();
        
        final Number valorSaldo = result == null ? 0 : (Number)result;
        
        final FiltroContas filtroContas = new FiltroContas();
        filtroContas.setConta(conta);
        
        final FiltroLancamentos filtroLancamentos = new FiltroLancamentos();
        filtroLancamentos.setFiltroContas(filtroContas);
        
        final FiltroFatos filtroFatos = new FiltroFatos();
        filtroFatos.setDataMinima(periodo.getDataMinima());
        filtroFatos.setDataMaxima(periodo.getDataMaxima());
        filtroLancamentos.setFiltroFatos(filtroFatos);
        
        final List<Lancamento> lancamentos = 
            this.lancamentosDao.listar(em, filtroLancamentos);
        
        final List<Item> itens = 
            lancamentos
                .stream()
                .map(l -> new Item(l.getFato(), l.getValor()))
                .collect(toList());
            
        return new Extrato(id,valorSaldo,itens);
    }

    @Override
    @SuppressWarnings("unused")
    public void criar(final EntityManager em, final Extrato e) {
        throw new UnsupportedOperationException("Não se criam Extratos.");

    }

    @Override
    @SuppressWarnings("unused")
    public Extrato atualizar(final EntityManager em, final Extrato e)
            throws DalException {
        throw new UnsupportedOperationException("Não se atualizam Extratos.");
    }

    @Override
    @SuppressWarnings("unused")
    public void deletar(final EntityManager em, final Id key) {
        throw new UnsupportedOperationException("Não se deletam Extratos.");
    }

}
