package com.github.brunoabdon.planinha.rest;

import static lombok.AccessLevel.PACKAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("contas/{conta_id}/extratos")
public class Extratos {

    @Autowired
    private Facade<ContaVO,Integer,?,?> contaFacade;

    @Autowired
    private Facade<Extrato, Extrato.Id, Integer, ?> facade;

    @GetMapping
    public Page<Extrato> listar(
            @PathVariable("conta_id")
            final Integer idConta,

            @PageableDefault
            final Pageable pageable)
                throws EntidadeInexistenteException {

        log.debug("Listando extratos da conta {}.", idConta);

        final Page<Extrato> extratosDaConta = facade.lista(idConta, pageable);

        if(extratosDaConta.isEmpty()) {
            this.contaFacade.pega(idConta); //excecao se nao existir a conta
        }

        return extratosDaConta;
    }

    @GetMapping("{periodo}")
    public Extrato pegar(
            @PathVariable("conta_id") final Integer idConta,
            @PathVariable("periodo") final Periodo periodo)
                throws EntidadeInexistenteException {

        log.debug("Pegando extrato da conta {} em {}.",idConta, periodo);

        final Extrato.Id id = new Id(new ContaVO(idConta), periodo);

        return facade.pega(id);
    }

}
