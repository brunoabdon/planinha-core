package com.github.brunoabdon.planinha.rest.server;

import static java.lang.Integer.parseInt;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.gastoso.Lancamento.Id;
import com.github.brunoabdon.gastoso.dal.LancamentosDao;

/**
 *
 * @author bruno
 */
@Path("operacoes/{operacaoId}/movimentacoes")
@Produces(MediaType.APPLICATION_JSON)
public class Movimentacoes 
        extends AbstractRestCrud<Lancamento, Lancamento.Id, Integer> {

	@Inject
    private LancamentosDao dao;
	
    @Override
    protected Dao<Lancamento, Lancamento.Id> getDao() {
        return this.dao;
    }

    @Override
    protected Lancamento.Id getFullId(final Integer contaId) {
        final String operacaoIdParam = 
            uriInfo.getPathParameters().getFirst("operacaoId");
        
        final Integer fatoId = parseInt(operacaoIdParam);
        
        return new Lancamento.Id(fatoId,contaId); 
    }

    @Override
    protected void defineChave(final Lancamento lancamento, final Id id) {
    	lancamento.setId(id);
	}
}
