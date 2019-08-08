package com.github.brunoabdon.planinha.rest.server;

import static java.time.LocalDate.now;
import static java.time.Period.between;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.github.brunoabdon.commons.util.modelo.Periodo;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.planinha.Extrato;

@Path("contas/{contaId}/extratos")
public class ExtratosDaConta {

    public ExtratosDaConta() {
    }

    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    public List<Extrato> list(@PathParam("contaId") final int contaId){
         
        final Conta conta = entityManager.find(Conta.class, contaId);
        if(conta == null) throw new NotFoundException();

        final LocalDate dataIncial = 
            entityManager
                .createQuery(
                    "select min(l.fato.dia) from Lancamento l where l.conta = :conta",
                    LocalDate.class
                )
                .setParameter("conta", conta)
                .getResultStream()
                .findAny()
                .orElseGet(() -> LocalDate.now())
                .withDayOfMonth(1);
        
        return        
            Stream
                .iterate(dataIncial, d -> d.plusMonths(1))
                .limit(between(dataIncial, now().plusMonths(1)).toTotalMonths())
                .map(d -> new Periodo(d, d.plusMonths(1).minusDays(1)))
                .map(p -> new Extrato.Id(conta, p))
                .map(Extrato::new)
                .collect(Collectors.toList());
    }

}
