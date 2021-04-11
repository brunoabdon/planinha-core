package com.github.brunoabdon.planinha.rest;

import static lombok.AccessLevel.PACKAGE;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.planinha.modelo.Movimentacao;
import com.github.brunoabdon.planinha.modelo.Movimentacao.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(PACKAGE)
@RestController
@RequestMapping("operacoes/{operacao_id}/movimentacoes")
public class Movimentacoes {

    @Getter
    @Setter
    @ToString
    public static class Atualizacao {
        private Integer valor;
    }

    @Autowired
    private Facade<Movimentacao, Id, Integer, Integer> facade;

    @GetMapping
    public List<Movimentacao> listar(
            @PathParam("operacao_id") final Integer idOperacao)
                throws EntidadeInexistenteException {

        log.debug("Listando movimentações da operação {}.",idOperacao);
        return facade.lista(idOperacao);
    }

    @GetMapping("{conta_id}")
    public Movimentacao pegar(
            @PathParam("operacao_id") final Integer idOperacao,
            @PathParam("conta_id") final Integer idConta)
                throws EntidadeInexistenteException {

        log.debug(
            "Pegando movimentação da conta {} em {}.",
            idConta, idOperacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

        return facade.pega(id);
    }

    @PutMapping("{conta_id}")
    public Movimentacao atualizar(
            @PathParam("operacao_id") final Integer idOperacao,
    		@PathParam("conta_id") final Integer idConta,
    		@NotNull final Atualizacao atualizacao) throws BusinessException {

        log.debug(
            "Atualizando pra {} a movimentação da conta {} em {}.",
            idConta, idOperacao, atualizacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

        return facade.atualiza(id, atualizacao.getValor());
    }

    @DeleteMapping("{conta_id}")
    public void deletar(
            @PathParam("operacao_id") final Integer idOperacao,
            @PathParam("conta_id") final Integer idConta)
                throws BusinessException {

        log.debug(
            "Deletando a movimentação da conta {0} em {1}.",
            idConta, idOperacao
        );

        final Movimentacao.Id id = new Movimentacao.Id(idOperacao,idConta);

		facade.deleta(id);
    }

    @PostMapping
    public Movimentacao criar(
            @PathParam("operacao_id") final Integer idOperacao,
            @NotNull final Movimentacao payload) throws BusinessException {

        log.debug(
            "Criando movimentação {} na operação {}.",
            payload, idOperacao
        );

        final Movimentacao.Id id = new Id(idOperacao,payload.getConta());

        final Movimentacao movimentacao =
            new Movimentacao(id, payload.getValor());

        return facade.cria(movimentacao);
    }
}
