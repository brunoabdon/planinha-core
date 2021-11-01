package com.github.brunoabdon.planinha.rest;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.rest.assembler.RepresentationModelsAssembler;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Movimentacao.Id;
import com.github.brunoabdon.planinha.rest.model.MovimentacaoModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("/operacoes/{operacao_id}/movimentacoes")
public class Movimentacoes {

    @Getter
    @Setter
    @ToString
    public static class Atualizacao {
        private Integer valor;
    }

    @Autowired
    private Facade<Movimentacao, Id, Integer, Integer> facade;

    @Autowired
    private PagedResourcesAssembler<Movimentacao> pgAsmblr;

    @Autowired
    private RepresentationModelsAssembler<Movimentacao,MovimentacaoModel> movimentacaoAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<MovimentacaoModel>> listar(
            @PathVariable("operacao_id") final Integer idOperacao)
                throws EntidadeInexistenteException {

        log.debug("Listando movimentações da operação {}.",idOperacao);

        final Page<Movimentacao> page =
            facade.lista(idOperacao, null);

        final Link self =
            linkTo(
                methodOn(Movimentacoes.class).listar(idOperacao)
            ).withSelfRel();

        final PagedModel<MovimentacaoModel> pageModel =
            pgAsmblr.toModel(page, movimentacaoAssembler, self);

        return new ResponseEntity<>(pageModel, HttpStatus.OK);
    }

    @GetMapping("{conta_id}")
    public MovimentacaoModel pegar(
            @PathVariable("operacao_id") final Integer idOperacao,
            @PathVariable("conta_id") final Integer idConta)
                throws EntidadeInexistenteException {

        log.debug(
            "Pegando movimentação da conta {} em {}.",
            idConta, idOperacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

        final Movimentacao movimentacao = facade.pega(id);

        return movimentacaoAssembler.toFullModel(movimentacao);
    }

    @PutMapping("{conta_id}")
    public MovimentacaoModel atualizar(
            @PathVariable("operacao_id") final Integer idOperacao,
            @PathVariable("conta_id") final Integer idConta,
    		@RequestBody final Atualizacao atualizacao)
		        throws BusinessException {

        log.debug(
            "Atualizando pra {} a movimentação da conta {} em {}.",
            idConta, idOperacao, atualizacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

        final Movimentacao movimentacao =
            facade.atualiza(id, atualizacao.getValor());

        return movimentacaoAssembler.toFullModel(movimentacao);
    }

    @DeleteMapping("{conta_id}")
    public void deletar(
            @PathVariable("operacao_id") final Integer idOperacao,
            @PathVariable("conta_id") final Integer idConta)
                throws BusinessException {

        log.debug(
            "Deletando a movimentação da conta {} em {}.",
            idConta, idOperacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

		facade.deleta(id);
    }

    @PostMapping
    public MovimentacaoModel criar(
            @PathVariable("operacao_id") final Integer idOperacao,
            @NotNull @RequestBody  final Movimentacao payload)
                throws BusinessException {

        log.debug(
            "Criando movimentação {} na operação {}.",
            payload, idOperacao
        );

        final Movimentacao.Id id = new Id(idOperacao,payload.getConta());

        final Movimentacao movimentacao =
            new Movimentacao(id, payload.getValor());

        final Movimentacao movimentacaoCriada = facade.cria(movimentacao);

        return movimentacaoAssembler.toFullModel(movimentacaoCriada);
    }
}
