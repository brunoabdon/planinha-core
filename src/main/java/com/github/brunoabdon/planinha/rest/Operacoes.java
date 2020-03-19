package com.github.brunoabdon.planinha.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.logging.Logger.Level.INFO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.planinha.facade.OperacaoFacade;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.modelo.Periodo;

@Path("operacoes")
@ApplicationScoped
public class Operacoes {

    @Inject
    Logger logger;

    @Inject
    OperacaoFacade facade;

    @GET
    @Produces(APPLICATION_JSON)
    public Response listar(
			@QueryParam("mes") final YearMonth mes,
			@QueryParam("dataMinima") final LocalDate dataMinima,
			@QueryParam("dataMaxima") final LocalDate dataMaxima) {

        logger.logv(
    		INFO, "Listando operações por {0},{1}->{2}.",
    		mes, dataMinima, dataMaxima
		);

        final boolean temMes = mes != null;
		if(temMes && (dataMinima != null || dataMaxima != null)) {
        	throw new BadRequestException("Ou mes ou limite de datas.");
        }

		final Periodo periodo =
			temMes ? Periodo.mes(mes) : new Periodo(dataMinima, dataMaxima);

        final List<Operacao> operacoes = facade.listar(periodo);

        return Response.ok(operacoes).build();
    }

    @GET
    @Path("{operacao_id}")
    @Produces(APPLICATION_JSON)
    public Response pegar(@PathParam("operacao_id") final Integer idOperacao)
            throws EntidadeInexistenteException {

        logger.logv(INFO, "Pegando operacao {0}.",idOperacao);

        final Operacao operacao = facade.pega(idOperacao);

        return Response.ok(operacao).build();
    }
}