package com.github.brunoabdon.planinha.rest;

import static lombok.AccessLevel.PACKAGE;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.modelo.Periodo;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("operacoes")
public class Operacoes {

    @Autowired
    private Facade<Operacao, Integer, Periodo, ?> facade;

    @GetMapping
    public List<Operacao> listar(
            @RequestParam(name="mes", required = false)
            final YearMonth mes,

            @DateTimeFormat(iso = ISO.DATE)
            @RequestParam(name = "dataMinima", required = false)
            final LocalDate dataMinima,

            @DateTimeFormat(iso = ISO.DATE)
            @RequestParam(name = "dataMaxima", required = false)
            final LocalDate dataMaxima)
		        throws EntidadeInexistenteException {

        log.debug(
    		"Listando operações por {},{}->{}.",
    		mes, dataMinima, dataMaxima
		);

        final Periodo periodoMes =
            mes == null ? Periodo.SEMPRE : Periodo.mes(mes);

        final Periodo periodoDatas = Periodo.of(dataMinima, dataMaxima);

		final Periodo periodo = periodoMes.intersecao(periodoDatas);

		return facade.lista(periodo);
    }

    @GetMapping("{operacao_id}")
    public Operacao pegar(@PathVariable("operacao_id") final Integer idOperacao)
            throws EntidadeInexistenteException {

        log.debug("Pegando operacao {}.",idOperacao);

        return facade.pega(idOperacao);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable("id") final Integer id)
			throws BusinessException {

        log.debug("Deletando operacao de id {}.",id);

		facade.deleta(id);
    }

    @PostMapping
    public Operacao criar(@RequestBody final Operacao operacao)
            throws BusinessException {

        log.debug("Criando operação {}.", operacao);

        return facade.cria(operacao);
    }
}