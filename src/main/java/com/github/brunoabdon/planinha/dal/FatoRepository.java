package com.github.brunoabdon.planinha.dal;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.brunoabdon.commons.dal.Repositorio;
import com.github.brunoabdon.commons.modelo.Periodo;
import com.github.brunoabdon.planinha.dal.modelo.Fato;

@Repository
public interface FatoRepository extends Repositorio<Fato, Integer> {


    public static final Logger log = getLogger(FatoRepository.class);

    public default Stream<Fato> findByPeriodo(
            final Periodo periodo) {

        log.debug("Pesquisando fatos pelo perído {}.", periodo);

        final Stream<Fato> fatos;

        final  LocalDate inicio = periodo.getInicio();
        final  LocalDate fim = periodo.getFim();

        if(periodo.isFechado()){
            log.trace("Pesquisando fatos com dia entre {} e {}.", inicio, fim);
            fatos = this.findByDiaBetween(inicio,fim);
        } else if(Periodo.SEMPRE.equals(periodo)) {
            log.trace("Pesquisando fatos em qualquer dia.");
            fatos = findAll(Pageable.unpaged()).stream();
        } else if(inicio != null){
            log.trace("Pesquisando fatos a partir do dia {}.", inicio);
            fatos = findByDiaGreaterThanEqual(inicio);
        } else {
            log.trace("Pesquisando fatos até o dia {}.", fim);
            fatos = findByDiaLessThanEqual(fim);
        }
        return fatos;
    }

    public Stream<Fato> findByDiaGreaterThanEqual(final LocalDate dia);

    public Stream<Fato> findByDiaLessThanEqual(final LocalDate dia);

    public Stream<Fato> findByDiaBetween(
            final LocalDate dataMinima,
            final LocalDate dataMaxima);


}
