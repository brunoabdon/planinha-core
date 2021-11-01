package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.Operacoes;
import com.github.brunoabdon.planinha.rest.model.OperacaoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperacaoAssembler extends IdentifiableModelAssembler<Operacao, OperacaoModel> {

    @Autowired
    protected OperacaoAssembler() {
        super(Operacoes.class,OperacaoModel.class);
    }
}
