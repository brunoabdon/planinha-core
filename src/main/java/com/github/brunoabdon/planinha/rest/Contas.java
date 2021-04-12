package com.github.brunoabdon.planinha.rest;

import static lombok.AccessLevel.PACKAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.ContaVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("contas")
public class Contas {

    @Autowired
    private Facade<ContaVO,Integer,String,String> facade;

    @GetMapping
    public Page<ContaVO> listar(
            @RequestParam(name="parteDoNome", required = false)
            final String parteDoNome,

            @PageableDefault(sort = "nome")
            final Pageable pageable)
                throws EntidadeInexistenteException {

        log.debug("Listando contas");

        return facade.lista(parteDoNome,pageable);
    }

    @GetMapping("{conta_id}")
    public ContaVO pegar(@PathVariable("conta_id") final Integer idConta)
            throws EntidadeInexistenteException {

        log.debug("Pegando conta {}.",idConta);

        return facade.pega(idConta);

    }

    @PutMapping("{conta_id}")
    public ContaVO atualizar(
    		@PathVariable("conta_id") final Integer idConta,
    		@RequestBody final ContaVO conta) throws BusinessException {

        log.debug("Atualizando conta {} pra {}.",idConta, conta);

        final String nome = conta.getNome();
        return facade.atualiza(idConta, nome);
    }

    @DeleteMapping("{conta_id}")
    public void deletar(@PathVariable("conta_id") final Integer idConta)
			throws BusinessException {

        log.debug("Deletando conta de id {}.",idConta);
		facade.deleta(idConta);
    }

    @PostMapping
    public ContaVO criar(@RequestBody final ContaVO conta)
            throws BusinessException {
        log.debug("Criando conta {}.",conta);

        return facade.cria(conta);
    }
}
