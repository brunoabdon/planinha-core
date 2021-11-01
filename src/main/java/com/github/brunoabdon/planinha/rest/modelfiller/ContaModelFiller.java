package com.github.brunoabdon.planinha.rest.modelfiller;

import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import org.springframework.stereotype.Component;

@Component
public class ContaModelFiller implements ModelFiller<ContaModel, ContaVO> {

    @Override
    public void fillModel(final ContaModel model, final ContaVO conta){
        model.setId(conta.getId());
        model.setNome(conta.getNome());
    }
}
