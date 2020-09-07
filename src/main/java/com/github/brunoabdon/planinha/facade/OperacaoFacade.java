package com.github.brunoabdon.planinha.facade;

import static org.jboss.logging.Logger.Level.DEBUG;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.dal.FatoConsultaFiltro;
import com.github.brunoabdon.planinha.dal.modelo.Fato;
import com.github.brunoabdon.planinha.dal.modelo.Lancamento;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.modelo.Periodo;

@ApplicationScoped
public class OperacaoFacade
        implements Facade<Operacao, Integer, Periodo, Fato>{


	@Inject
	Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    FatoConsultaFiltro operacaoConsulta;

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Operacao cria(final Operacao operacao) throws BusinessException {
    	logger.logv(DEBUG, "Criando operação {0}.", operacao);
    	em.persist(operacao);

    	operacao.getMovimentacoes().forEach(m -> this.cria(operacao,m));

        return operacao;
    }

    private void cria(final Operacao operacao, final Lancamento movimentacao){
        logger.logv(
            DEBUG, "Criando movimentação {0} da operação {1}.",
            movimentacao, operacao
        );

        movimentacao.withFato(operacao.getId());

        em.persist(movimentacao);
    }

    @Override
    public Operacao pega(final Integer id) throws EntidadeInexistenteException{
    	logger.logv(DEBUG, "Pegando operação de id {0}.", id);

        final Operacao operacao = em.find(Operacao.class, id);

        if(operacao == null)
            throw new EntidadeInexistenteException(Operacao.class, id);

        return operacao;
    }

    @Override
    public List<Operacao> lista(final Periodo filtro) {
    	logger.logv(DEBUG, "Listando operações por {0}.", filtro);
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public Operacao atualiza(final Integer key, final Fato atualizacao)
            throws EntidadeInexistenteException, BusinessException {

    	final Operacao operacao = pega(key);

    	operacao.setFato(atualizacao);

    	return operacao;
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer key)
    		throws EntidadeInexistenteException, BusinessException {
    	logger.logv(DEBUG, "Deletando operação de id {0}.", key);
        final Object operacao = pega(key);
		em.remove(operacao );
    }


}
