package com.github.brunoabdon.planinha.rest.server;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.github.brunoabdon.commons.dal.DalException;
import com.github.brunoabdon.gastoso.Fato;
import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.gastoso.dal.FatosDao;
import com.github.brunoabdon.gastoso.dal.LancamentosDao;
import com.github.brunoabdon.planinha.Movimentacao;
import com.github.brunoabdon.planinha.Operacao;

@Path(Operacoes.PATH)
public class Operacoes {

	@Inject
	private Logger logger;
	
    protected static final String PATH = "operacoes";
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    private final FatosDao fatosDao;
    private final LancamentosDao lancamentosDao;
    
    public Operacoes() {
    	this.fatosDao = new FatosDao(); //pq não são singletons?
    	this.lancamentosDao = new LancamentosDao();
	}
        
	
    @POST
    @Transactional
    public Response criar(final Operacao operacao) 
    		throws URISyntaxException {
    	
    	ResponseBuilder rb;
    	
    	try {
			final Fato fato = operacao.getFato();
			this.fatosDao.criar(entityManager, fato);
			
			for (final Movimentacao movimentacao:operacao.getMovimentacoes()){
				final Lancamento lancamento = 
					new Lancamento(
						fato,
						movimentacao.getConta(), 
						movimentacao.getValor()
					);
				this.lancamentosDao.criar(entityManager, lancamento);
			}
			
            final URI uri = new URI(PATH + fato.getId());
			
			rb = Response.created(uri).entity(operacao);
			
			
		} catch (final DalException e) {
            logger.logv(Level.ERROR, e, "Erro ao tentar criar {0}.", operacao);
            rb = 
                Response
                	.status(Response.Status.CONFLICT)
                    .entity(e.getMessage());
		}
    	return rb.build();
    }
}
