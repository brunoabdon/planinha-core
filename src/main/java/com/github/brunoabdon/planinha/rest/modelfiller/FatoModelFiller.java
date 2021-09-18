package com.github.brunoabdon.planinha.rest.modelfiller;

import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.FatoVO;
import com.github.brunoabdon.planinha.modelo.Operacao;
import com.github.brunoabdon.planinha.rest.model.FatoModel;
import org.springframework.stereotype.Component;

@Component
public class FatoModelFiller implements ModelFiller<FatoModel, Operacao> {

    @Override
    public void fillModel(final FatoModel model, final Operacao value) {
        final FatoVO fatoDaOperacao = value.getFato();
        model.setDia(fatoDaOperacao.getDia());
        model.setDescricao(fatoDaOperacao.getDescricao());
    }
}
