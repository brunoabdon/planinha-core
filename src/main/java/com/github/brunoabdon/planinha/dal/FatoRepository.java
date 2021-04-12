package com.github.brunoabdon.planinha.dal;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.planinha.dal.modelo.Fato;

@Repository
public interface FatoRepository extends Repositorio<Fato, Integer> {

    public static final Logger log = getLogger(FatoRepository.class);

    public default Page<Fato> findByPeriodo(
            final Periodo periodo, final Pageable pageable) {

        log.debug("Pesquisando fatos pelo perído {} ({}).", periodo, pageable);

        final Page<Fato> fatos;

        final  LocalDate inicio = periodo.getInicio();
        final  LocalDate fim = periodo.getFim();

        if(periodo.isFechado()){
            log.trace(
                "Pesquisando fatos com dia entre {} e {} ({}).",
                inicio, fim, pageable
            );
            fatos = this.findByDiaBetween(inicio,fim,pageable);
        } else if(Periodo.SEMPRE.equals(periodo)) {
            log.trace("Pesquisando fatos em qualquer dia.",pageable);
            fatos = findAll(pageable);
        } else if(inicio != null){
            log.trace(
                "Pesquisando fatos a partir do dia {} ({}).", inicio, pageable
            );
            fatos = findByDiaGreaterThanEqual(inicio, pageable);
        } else {
            log.trace("Pesquisando fatos até o dia {} ({}).", fim,pageable);
            fatos = findByDiaLessThanEqual(fim, pageable);
        }
        return fatos;
    }

    public Page<Fato> findByDiaGreaterThanEqual(
            final LocalDate dia,
            final Pageable pageable);

    public Page<Fato> findByDiaLessThanEqual(
            final LocalDate dia,
            final Pageable pageable);

    public Page<Fato> findByDiaBetween(
            final LocalDate dataMinima,
            final LocalDate dataMaxima,
            final Pageable pageable);
}
