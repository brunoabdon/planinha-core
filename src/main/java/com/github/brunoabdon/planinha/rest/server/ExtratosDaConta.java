package com.github.brunoabdon.planinha.rest.server;

import static java.time.Period.between;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.commons.collections4.CollectionUtils;

import com.github.brunoabdon.commons.util.modelo.Periodo;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.planinha.Extrato;

@Path("contas/{contaId}/extratos")
public class ExtratosDaConta {

    public ExtratosDaConta() {
        super();
    }

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public List<Extrato> list(@PathParam("contaId") final int contaId){
         
        final Conta conta = entityManager.find(Conta.class, contaId);
        if(conta == null) throw new NotFoundException();

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

}
