package com.github.brunoabdon.planinha.rest;

import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.modelo.Extrato;
import com.github.brunoabdon.planinha.modelo.Extrato.Id;
import com.github.brunoabdon.planinha.rest.model.ExtratoModel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Setter(PACKAGE)
@ExposesResourceFor(Extrato.class)
@RestController
@RequestMapping("/contas/{conta_id}/extratos")
public class Extratos {

    @Autowired
    private PagedResourcesAssembler<Extrato> pgAsmblr;

    @Autowired
    private RepresentationModelsAssembler<Extrato, ExtratoModel> extratoAssembler;

    @Autowired
    private Facade<ContaVO,Integer,?,?> contaFacade;

    @Autowired
    private Facade<Extrato, Extrato.Id, Integer, ?> facade;

    //TODO registrar handlers pra usar Periodo como tipo do argumento:
    //o.s.w.s.c.a.WebMvcConfigurer.addArgumentResolvers(...)
    @Autowired
    private Converter<String, Periodo> periodoConverter;

    @GetMapping
    public ResponseEntity<PagedModel<ExtratoModel>> listar(
            @PathVariable("conta_id")
            final Integer idConta,

            @PageableDefault
            final Pageable pageable)
                throws EntidadeInexistenteException {

        log.debug("Listando extratos da conta {} ({}).", idConta, pageable);

        final Page<Extrato> extratosDaConta = facade.lista(idConta, pageable);

        if(extratosDaConta.isEmpty()) {
            this.contaFacade.pega(idConta); //excecao se nao existir a conta
        }

        final Link self =
            linkTo(methodOn(Extratos.class).listar(idConta, pageable))
            .withSelfRel();

        final PagedModel<ExtratoModel> pageModel =
            pgAsmblr.toModel(extratosDaConta, extratoAssembler, self);

        return new ResponseEntity<>(pageModel, HttpStatus.OK);
    }

    @GetMapping("{periodo}")
    public ExtratoModel pegar(
            @PathVariable("conta_id") final Integer idConta,
            @PathVariable("periodo") final String periodoStr)
                throws EntidadeInexistenteException {

        log.debug("Pegando extrato {} da conta {}.", periodoStr, idConta);

        final Periodo periodo = periodoConverter.convert(periodoStr);

        final Extrato.Id id = new Id(new ContaVO(idConta), periodo);

        final Extrato extrato = facade.pega(id);

        return extratoAssembler.toFullModel(extrato);
    }
}
