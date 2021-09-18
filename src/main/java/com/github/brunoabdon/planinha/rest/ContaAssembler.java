package com.github.brunoabdon.planinha.rest;

import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import org.springframework.stereotype.Component;

@Component
public class ContaAssembler extends IdentifiableModelAssembler<ContaVO, ContaModel> {

    protected ContaAssembler() {
        super(ContaVO.class,ContaModel.class);
    }

    @Override
    public void fillModel(final ContaModel model, final ContaVO conta){
        model.setId(conta.getId());
        model.setNome(conta.getNome());
    }

}
