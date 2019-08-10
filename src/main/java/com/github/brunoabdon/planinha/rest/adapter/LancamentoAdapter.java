package com.github.brunoabdon.planinha.rest.adapter;

import javax.json.bind.adapter.JsonbAdapter;

import com.github.brunoabdon.gastoso.Lancamento;
import com.github.brunoabdon.planinha.Movimentacao;

public class LancamentoAdapter 
		implements JsonbAdapter<Lancamento, Movimentacao> {

	@Override
	public Movimentacao adaptToJson(final Lancamento lancamento) {
		return new Movimentacao(lancamento.getConta(),lancamento.getValor());
	}

	@Override
	public Lancamento adaptFromJson(final Movimentacao movimentacao) {
		return new Lancamento(movimentacao.getValor());
	}

}
