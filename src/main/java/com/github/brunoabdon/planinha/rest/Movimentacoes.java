package com.github.brunoabdon.planinha.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.logging.Logger.Level.INFO;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.planinha.facade.MovimentacaoFacade;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Movimentacao.Id;

@ApplicationScoped
@Path("operacoes/{operacao_id}/movimentacoes")
public class Movimentacoes {

    public static class Atualizacao{
        private Integer valor;

        public Integer getValor() {
            return valor;
        }

        public void setValor(final Integer valor) {
            this.valor = valor;
        }
    }

    @Inject
    Logger logger;

    @Inject
    MovimentacaoFacade facade;

    @GET
    @Produces(APPLICATION_JSON)
    public Response listar(
            @PathParam("operacao_id") final Integer idOperacao)
                throws EntidadeInexistenteException {
        logger.logv(INFO,"Listando movimentações da operação {0}.",idOperacao);
        final List<Movimentacao> movimentacoes = facade.lista(idOperacao);
        return Response.ok(movimentacoes).build();
    }

    @GET
    @Path("{conta_id}")
    @Produces(APPLICATION_JSON)
    public Response pegar(
            @PathParam("operacao_id") final Integer idOperacao,
            @PathParam("conta_id") final Integer idConta)
                throws EntidadeInexistenteException {

        logger.logv(
            INFO, "Pegando movimentação da conta {0} em {1}.",
            idConta, idOperacao
        );


        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

        final Movimentacao movimentacao = facade.pega(id);

        return Response.ok(movimentacao).build();
    }

    @PUT
    @Path("{conta_id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response atualizar(
            @PathParam("operacao_id") final Integer idOperacao,
    		@PathParam("conta_id") final Integer idConta,
    		@NotNull final Atualizacao atualizacao)
				throws EntidadeInexistenteException, BusinessException {

        logger.logv(
            INFO, "Atualizando pra {2} a movimentação da conta {0} em {1}.",
            idConta, idOperacao, atualizacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);
		final Movimentacao movimentacaoAtualizada =
	        facade.atualiza(id, atualizacao.getValor());

        return Response.ok(movimentacaoAtualizada).build();
    }

    @DELETE
    @Path("{conta_id}")
    public Response deletar(
            @PathParam("operacao_id") final Integer idOperacao,
            @PathParam("conta_id") final Integer idConta)
                throws EntidadeInexistenteException, BusinessException {

        logger.logv(
            INFO, "Deletando a movimentação da conta {0} em {1}.",
            idConta, idOperacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

		facade.deleta(id);

        return Response.ok().build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response criar(
            @PathParam("operacao_id") final Integer idOperacao,
            @NotNull final Movimentacao payload)
                throws BusinessException, EntidadeInexistenteException {

        logger.logv(
            INFO, "Criando movimentação {0} na operação {1}.",
            payload, idOperacao
        );

        final Movimentacao.Id id = new Id(idOperacao,payload.getConta());

        final Movimentacao movimentacao =
            new Movimentacao(id, payload.getValor());

        final Movimentacao movimentacaoCriada = facade.cria(movimentacao);

        return Response.ok(movimentacaoCriada).build();
    }
}
