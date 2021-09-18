package com.github.brunoabdon.planinha.rest.assembler;

import com.github.brunoabdon.commons.rest.assembler.IdentifiableModelAssembler;
import com.github.brunoabdon.commons.rest.modelfiller.ModelFiller;
import com.github.brunoabdon.planinha.modelo.ContaVO;
import com.github.brunoabdon.planinha.rest.model.ContaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContaAssembler extends IdentifiableModelAssembler<ContaVO, ContaModel> {

    @Autowired
    protected ContaAssembler(final ModelFiller<ContaModel,ContaVO> filler) {
        super(ContaVO.class,ContaModel.class,filler);
    }
}
