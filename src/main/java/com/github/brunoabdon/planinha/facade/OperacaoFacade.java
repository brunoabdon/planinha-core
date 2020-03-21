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
import com.github.brunoabdon.planinha.dal.OperacaoConsultaFiltro;
import com.github.brunoabdon.planinha.modelo.Fato;
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
    OperacaoConsultaFiltro operacaoConsulta;

    @Override
    public Operacao cria(final Operacao operacao) throws BusinessException {
    	logger.logv(DEBUG, "Criando operação {0}.", operacao);
        return null;
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
    public List<Operacao> listar(final Periodo filtro) {
    	logger.logv(DEBUG, "Listando operações por {0}.", filtro);
        return operacaoConsulta.listar(filtro);
    }

    @Override
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
