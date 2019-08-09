package com.github.brunoabdon.planinha.rest.server;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.github.brunoabdon.commons.rest.AbstractRestCrud;
import com.github.brunoabdon.gastoso.Fato;
import com.github.brunoabdon.gastoso.dal.FatosDao;
import com.github.brunoabdon.gastoso.system.FiltroFatos;

/**
 *
 * @author bruno
 */
@Path("fatos")
@Produces(MediaType.APPLICATION_JSON)
public class Fatos extends AbstractRestCrud<Fato,Integer>{

    private final FatosDao dao;

    public Fatos() {
        this.dao = new FatosDao();
    }

    @Override
    public FatosDao getDao() {
        return dao;
    }

    @GET
    public Response listar(
        final @Context Request request,
        final @Context HttpHeaders httpHeaders,
        final @QueryParam("mes") YearMonth mes,
        @QueryParam("dataMin") LocalDate dataMinima,
        @QueryParam("dataMax") LocalDate dataMaxima){

        final List<Fato> fatos;

        
        //mudar pra usar hibernate validation
        if((mes == null && dataMaxima == null)
            || (mes != null && (dataMaxima != null || dataMinima != null))){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        if(mes != null){
            dataMinima = mes.atDay(1);
            dataMaxima  = mes.atEndOfMonth();
        } else if(dataMinima == null) {
            dataMinima = dataMaxima.minusMonths(1);
        }
            
        final FiltroFatos filtroFatos = new FiltroFatos();
        filtroFatos.setDataMaxima(dataMaxima);
        filtroFatos.setDataMinima(dataMinima);
        
        fatos = dao.listar(entityManager, filtroFatos);
        
        fatos.sort(
            (f1,f2) -> {
                int diff = f1.getDia().compareTo(f2.getDia());
                if(diff == 0) diff = f1.getId() - f2.getId();
                return diff;
            });
            

        return buildResponse(request, httpHeaders, fatos);
    }
}