package com.github.brunoabdon.planinha.dal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.brunoabdon.commons.dal.AbstractDao;
import com.github.brunoabdon.commons.dal.DalException;
import com.github.brunoabdon.gastoso.Fato;
import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.gastoso.dal.FatosDao;
import com.github.brunoabdon.gastoso.dal.LancamentosDao;
import com.github.brunoabdon.planinha.Movimentacao;
import com.github.brunoabdon.planinha.Operacao;

@ApplicationScoped
public class OperacoesDao extends AbstractDao<Operacao, Integer> {

    @Inject
    private FatosDao fatosDao;
    
    @Inject
    private LancamentosDao lancamentosDao;
    
    public OperacoesDao() {
        super(Operacao.class);
    }
    
    @Override
    public void criar(final Operacao operacao) throws DalException {

        final Fato fato = operacao.getFato();
        this.fatosDao.criar(fato);
        
        for (final Movimentacao movimentacao:operacao.getMovimentacoes()){
            final Lancamento lancamento = 
                new Lancamento(
                    fato,
                    movimentacao.getConta(),
                    movimentacao.getValor()
                );
            this.lancamentosDao.criar(lancamento);
        }
        
        operacao.setId(fato.getId());
    }

    
    @Override
    public void deletar(final Integer key) throws DalException {
        
        getEntityManager()
          .createNamedQuery("Lancamento.deletarPorFato")
          .setParameter("fato", new Fato(key))
          .executeUpdate();
        
        this.fatosDao.deletar(key);
    }
}