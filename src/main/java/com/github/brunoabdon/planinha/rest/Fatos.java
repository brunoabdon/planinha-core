package com.github.brunoabdon.planinha.rest;

import static lombok.AccessLevel.PACKAGE;

import javax.validation.Valid;

import com.github.brunoabdon.planinha.rest.model.FatoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Operacao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("/operacoes/{operacao_id}/fato")
public class Fatos {

    @Autowired
    private Facade<Operacao, Integer, ?, FatoVO> facade;

    @Autowired
    private RepresentationModelAssembler<Operacao,FatoModel> assembler;

    @GetMapping
    public FatoModel pegar(@PathVariable("operacao_id") final Integer idOperacao)
            throws EntidadeInexistenteException {

        log.debug("Pegando fato da operação {}.",idOperacao);

        final Operacao operacao = facade.pega(idOperacao);

        return assembler.toModel(operacao);
    }

    @PutMapping
    public FatoModel atualizar(
            @PathVariable("operacao_id") final Integer idOperacao,
    		@Valid @RequestBody final FatoVO patch) throws BusinessException {

        log.debug(
    		"Atualizando fato da operacao {} pra {}.",
    		idOperacao, patch
		);

        final Operacao operacao = facade.atualiza(idOperacao, patch);

        return assembler.toModel(operacao);
    }
}
