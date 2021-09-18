package com.github.brunoabdon.planinha.rest;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.model.OperacaoModel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("/operacoes")
public class Operacoes {

    @Autowired
    private PagedResourcesAssembler<Operacao> pgAsmblr;

    @Autowired
    private Facade<Operacao, Integer, Periodo, ?> facade;

    @Autowired
    private RepresentationModelAssembler<Operacao, OperacaoModel> operacaoAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<OperacaoModel>> listar(
            @RequestParam(name="mes", required = false)
            final YearMonth mes,

            @DateTimeFormat(iso = ISO.DATE)
            @RequestParam(name = "dataMinima", required = false)
            final LocalDate dataMinima,

            @DateTimeFormat(iso = ISO.DATE)
            @RequestParam(name = "dataMaxima", required = false)
            final LocalDate dataMaxima,

            @PageableDefault(sort = "dia")
            final Pageable pageable)
		        throws EntidadeInexistenteException {

        log.debug(
    		"Listando operações por no mês {} entre {} e {} ({}).",
    		mes, dataMinima, dataMaxima, pageable
		);

        final Periodo periodoMes =
            mes == null ? Periodo.SEMPRE : Periodo.mes(mes);

        final Periodo periodoDatas = Periodo.of(dataMinima, dataMaxima);

		final Periodo periodo = periodoMes.intersecao(periodoDatas);

        final Page<Operacao> page = facade.lista(periodo, pageable);

        final Link self =
            linkTo(
                methodOn(Operacoes.class).listar(
                    mes,dataMinima,dataMaxima, pageable
                )
            ).withSelfRel();

        return
            new ResponseEntity<>(
                    pgAsmblr.toModel(page,operacaoAssembler, self),
                    HttpStatus.OK
            );
    }

    @GetMapping("{operacao_id}")
    public OperacaoModel pegar(@PathVariable("operacao_id") final Integer idOperacao)
            throws EntidadeInexistenteException {

        log.debug("Pegando operacao {}.",idOperacao);

        final Operacao operacao = facade.pega(idOperacao);

        return operacaoAssembler.toModel(operacao);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable("id") final Integer id)
			throws BusinessException {

        log.debug("Deletando operacao de id {}.",id);

		facade.deleta(id);
    }

    @PostMapping
    public OperacaoModel criar(@RequestBody final Operacao operacao)
            throws BusinessException {

        log.debug("Criando operação {}.", operacao);

        final Operacao operacaoVO = facade.cria(operacao);

        return operacaoAssembler.toModel(operacaoVO);
    }
}