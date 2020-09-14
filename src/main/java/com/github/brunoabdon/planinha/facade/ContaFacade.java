package com.github.brunoabdon.planinha.facade;

import static java.util.stream.Collectors.toList;
import static org.jboss.logging.Logger.Level.DEBUG;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.planinha.dal.ContaConsultaFiltro;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.ContaVO;

@ApplicationScoped
public class ContaFacade implements Facade<ContaVO,Integer,String,String> {

    @Inject Logger logger;

    @PersistenceContext
    EntityManager em;

    @Inject
    IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapper;

    @Inject
    ContaConsultaFiltro consultaPorFiltro;

    @Override
    public ContaVO pega(final Integer id) throws EntidadeInexistenteException{

        logger.logv(DEBUG, "Pegando conta de id {0}.", id);

        final Conta conta = pega_(id);

        return mapper.toVO(conta);
    }

    private Conta pega_(final Integer id) throws EntidadeInexistenteException {

        logger.logv(DEBUG, "Pegando conta (Entity) de id {0}.", id);

        final Integer k = mapper.toKey(id);

        final Conta conta = em.find(Conta.class, k);

        if(conta == null)
            throw new EntidadeInexistenteException(ContaVO.class, id);
        return conta;
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public ContaVO cria(final ContaVO conta) throws BusinessException{

        final Conta contaEntity = new Conta(conta.getNome());

        em.persist(contaEntity);

        return mapper.toVOSimples(contaEntity);
    }

    @Override
    public List<ContaVO> lista(final String parteDoNome){
        return
            this.consultaPorFiltro
                .listar(parteDoNome)
                .stream()
                .map(mapper::toVOSimples)
                .collect(toList());
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public ContaVO atualiza(
            final Integer id,
            final String nome)
        throws EntidadeInexistenteException, BusinessException{

        final Integer k = mapper.toKey(id);

        final Conta conta = pega_(k);

        conta.setNome(nome);

        return mapper.toVO(conta);
    }

    @Override
    @Transactional(rollbackOn={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer id)
        throws EntidadeInexistenteException, BusinessException{

        final Conta conta = pega_(id);

        final boolean estaEmUso = this.consultaPorFiltro.estaEmUso(conta);

        //TODO dar a real
        if(estaEmUso) throw new BusinessException();

        em.remove(conta);
    }
}
