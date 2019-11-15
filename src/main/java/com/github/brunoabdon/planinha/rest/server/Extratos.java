package com.github.brunoabdon.planinha.rest.server;

import static java.lang.Integer.parseInt;
import static java.time.Period.between;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;

import com.github.brunoabdon.commons.dal.DalException;
import com.github.brunoabdon.commons.dal.Dao;
import com.github.brunoabdon.commons.rest.AbstractRestReadOnlyResource;
import com.github.brunoabdon.commons.util.modelo.Periodo;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.gastoso.dal.ContasDao;
import com.github.brunoabdon.planinha.Extrato;
import com.github.brunoabdon.planinha.Extrato.Id;
import com.github.brunoabdon.planinha.dal.ExtratosDao;
import com.github.brunoabdon.planinha.rest.paramconverters.ExtratoIdParamConverter;

@Path("contas/{contaId}/extratos")
@Produces(MediaType.APPLICATION_JSON)
public class Extratos extends AbstractRestReadOnlyResource<Extrato, Id, String> {

    private static final ExtratoIdParamConverter EXTRATO_ID_SERIALIZER = 
        new ExtratoIdParamConverter();
    
    
    
    @Inject
    private ExtratosDao extratosDao;

    @Inject
    private ContasDao contasDao;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    protected Dao<Extrato, Id> getDao() {
        return this.extratosDao;
    }

    @Override
    protected Id getFullId(final String extratoId) {
        final String contaIdParam = 
            uriInfo.getPathParameters().getFirst("contaId");
        
        final Integer contaId = parseInt(contaIdParam);
        final Conta conta = new Conta(contaId);
        
        final Periodo id = EXTRATO_ID_SERIALIZER.fromString(extratoId);
        
        return new Id(conta,id); 
    }
    
    @GET
    public List<Extrato> list(@PathParam("contaId") final int contaId){
         
        final Conta conta = pegaConta(contaId);

        final LocalDate hoje = LocalDate.now();
        
        final List<LocalDate> resultList = 
            entityManager
                .createQuery(
                    "select min(l.fato.dia) from Lancamento l where l.conta = :conta",
                    LocalDate.class
                )
            .setParameter("conta", conta)
            .getResultList();
        
        final LocalDate menorDia = 
            CollectionUtils.isEmpty(resultList)
                ? null
                : resultList.get(0); //pode dar null!
        
        final LocalDate diaDeReferencia = 
                menorDia != null ? menorDia : hoje;
        
        final LocalDate dataInicial = diaDeReferencia.withDayOfMonth(1);
        
        return        
            Stream
                .iterate(dataInicial, d -> d.plusMonths(1))
                .limit(between(dataInicial,hoje.plusMonths(1)).toTotalMonths())
                .map(d -> new Periodo(d, d.plusMonths(1).minusDays(1)))
                .map(p -> new Extrato.Id(conta, p))
                .map(Extrato::new)
                .collect(toList());
    }

    private Conta pegaConta(final int contaId) {
        Conta conta;
        try {
            conta = contasDao.find(contaId);
        } catch (DalException e) {
            throw new NotFoundException();
        }
        
        return conta;
    }
    
}
