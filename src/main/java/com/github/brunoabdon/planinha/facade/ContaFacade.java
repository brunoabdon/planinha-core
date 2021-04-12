package com.github.brunoabdon.planinha.facade;

import static lombok.AccessLevel.PACKAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.brunoabdon.commons.facade.BusinessException;
import com.github.brunoabdon.commons.facade.EntidadeInexistenteException;
import com.github.brunoabdon.commons.facade.Facade;
import com.github.brunoabdon.commons.facade.mappers.IdentifiableMapper;
import com.github.brunoabdon.planinha.dal.ContaRepository;
import com.github.brunoabdon.planinha.dal.LancamentoRepository;
import com.github.brunoabdon.planinha.dal.modelo.Conta;
import com.github.brunoabdon.planinha.modelo.ContaVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Setter(PACKAGE)
public class ContaFacade implements Facade<ContaVO,Integer,String,String> {

    @Autowired
    private IdentifiableMapper<Conta, Integer, ContaVO, Integer> mapper;

    @Autowired
    private ContaRepository repo;

    @Autowired
    private LancamentoRepository lancamentoRepo;

    @Override
    public ContaVO pega(final Integer id) throws EntidadeInexistenteException{

        log.debug("Pegando conta de id {}.", id);

        final Conta conta = pega2(id);

        return mapper.toVO(conta);
    }

    private Conta pega2(final Integer id) throws EntidadeInexistenteException {

        log.debug("Pegando conta (Entity) de id {}.", id);

        final Integer k = mapper.toKey(id);

        return
            repo.findById(k)
                .orElseThrow(
                    () -> new EntidadeInexistenteException(ContaVO.class, id)
                );
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public ContaVO cria(final ContaVO conta) throws BusinessException{

        log.debug("Criand conta{}", conta);

        final Conta contaEntity = new Conta(conta.getNome());

        final Conta contaSalva = repo.save(contaEntity);

        return mapper.toVOSimples(contaSalva);
    }

    @Override
    public Page<ContaVO> lista(
            final String parteDoNome,
            final Pageable pageable){

        log.debug("Listando contas por {} ({}).", parteDoNome, pageable);

        final Page<Conta> contas =
            parteDoNome  != null
                ? this.repo.findByNomeContainingIgnoreCase(parteDoNome,pageable)
                : this.repo.findAll(pageable);

        return contas.map(mapper::toVOSimples);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public ContaVO atualiza(
            final Integer id,
            final String nome)
        throws BusinessException{

        log.debug("Atualizando conta de id {} com {}.",id, nome);

        final Integer k = mapper.toKey(id);

        final Conta conta = pega2(k);

        conta.setNome(nome);

        return mapper.toVO(conta);
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class,BusinessException.class})
    public void deleta(final Integer id)
        throws BusinessException{

        log.debug("Deletando conta de id {}.",id);

        final Conta conta = pega2(id);

        final boolean estaEmUso = this.lancamentoRepo.existsByConta(conta);

        //TODO dar a real
        if(estaEmUso) throw new BusinessException();

        repo.delete(conta);
    }
}
