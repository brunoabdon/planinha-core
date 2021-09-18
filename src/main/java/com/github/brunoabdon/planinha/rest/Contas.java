package com.github.brunoabdon.planinha.rest;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Setter(PACKAGE)
@RestController
@ExposesResourceFor(ContaVO.class)
@RequestMapping("/contas")
public class Contas {

    @Autowired
    private PagedResourcesAssembler<ContaVO> pgAsmblr;

    @Autowired
    private Facade<ContaVO,Integer,String,String> facade;

    @Autowired
    private RepresentationModelAssembler<ContaVO, ContaModel> contaAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<ContaModel>> listar(
            @RequestParam(name="parteDoNome", required = false)
            final String parteDoNome,

            @PageableDefault(sort = "nome")
            final Pageable pageable)
                throws EntidadeInexistenteException {

        log.debug("Listando contas por {} ({}).",parteDoNome,pageable);

        final Page<ContaVO> page = facade.lista(parteDoNome,pageable);

        final Link self =
            linkTo(methodOn(Contas.class).listar(parteDoNome, pageable))
            .withSelfRel();

        return
            new ResponseEntity<>(
                pgAsmblr.toModel(page,contaAssembler, self),
                HttpStatus.OK
            );
    }

    @GetMapping("{conta_id}")
    public ContaModel pegar(
            @PathVariable("conta_id") final Integer idConta)
            throws EntidadeInexistenteException {

        log.debug("Pegando conta {}.",idConta);

        final ContaVO contaVO = facade.pega(idConta);

        return asModel(contaVO);
    }

    @PutMapping("{conta_id}")
    public ContaModel atualizar(
    		@PathVariable("conta_id") final Integer idConta,
    		@RequestBody final ContaVO conta) throws BusinessException {

        log.debug("Atualizando conta {} pra {}.",idConta, conta);

        final String nome = conta.getNome();

        final ContaVO contaVO = facade.atualiza(idConta, nome);

        return asModel(contaVO);
    }


    private ContaModel asModel(final ContaVO vo) {
        return contaAssembler.toModel(vo);
    }

    @DeleteMapping("{conta_id}")
    public void deletar(@PathVariable("conta_id") final Integer idConta)
			throws BusinessException {

        log.debug("Deletando conta de id {}.",idConta);

		facade.deleta(idConta);
    }

    @PostMapping
    public ContaModel criar(@RequestBody final ContaVO conta)
            throws BusinessException {
        log.debug("Criando conta {}.",conta);

        final ContaVO contaVO = facade.cria(conta);

        return asModel(contaVO);
    }
}
