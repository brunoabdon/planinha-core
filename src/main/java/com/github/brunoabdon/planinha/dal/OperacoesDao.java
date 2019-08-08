package com.github.brunoabdon.planinha.dal;

import javax.persistence.EntityManager;

import com.github.brunoabdon.commons.dal.AbstractDao;
import com.github.brunoabdon.commons.dal.DalException;
import com.github.brunoabdon.gastoso.Fato;
import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.gastoso.dal.FatosDao;
import com.github.brunoabdon.gastoso.dal.LancamentosDao;
import com.github.brunoabdon.planinha.Movimentacao;
import com.github.brunoabdon.planinha.Operacao;

public class OperacoesDao extends AbstractDao<Operacao, Integer> {

    private final FatosDao fatosDao;
    private final LancamentosDao lancamentosDao;
    
    public OperacoesDao() {
        super(Operacao.class);
        this.fatosDao = new FatosDao(); //pq não são singletons?
        this.lancamentosDao = new LancamentosDao();
    }
    
    @Override
    public void criar(final EntityManager em, final Operacao operacao) 
            throws DalException {

        final Fato fato = operacao.getFato();
        this.fatosDao.criar(em, fato);
        
        for (final Movimentacao movimentacao:operacao.getMovimentacoes()){
            final Lancamento lancamento = 
                new Lancamento(
                    fato,
                    movimentacao.getConta(), 
                    movimentacao.getValor()
                );
            this.lancamentosDao.criar(em, lancamento);
        }
        
        operacao.setId(fato.getId());
    }

    
    @Override
    public void deletar(final EntityManager em, final Integer key) 
            throws DalException {
        
        em.createNamedQuery("Lancamento.deletarPorFato")
          .setParameter("fato", new Fato(key))
          .executeUpdate();
        
        this.fatosDao.deletar(em, key);
    }
}
