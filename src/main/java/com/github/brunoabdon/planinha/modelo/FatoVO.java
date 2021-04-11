package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TODO documentaçaõ aqui.
 *
 * <p>Obs: Usa o {@linkplain #equals(Object) equals(..)} e {@linkplain
 * #hashCode() hashcode()} de {@link Object}, já que duas instâncias de {@link
 * FatoVO} com a mesma {@linkplain #getDescricao() descrição} num mesmo
 * {@linkplain #getDia() dia} não são necessáriamente a mesma entidade.</p>
 *
 * @author bruno
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FatoVO implements Serializable {

    private static final long serialVersionUID = -6816326120547843562L;

    private static final int TAMANHO_MAX_DESCRICAO_FATO = 70;

    @NotNull(message = "planinha.valid.fato.semDia")
    private LocalDate dia;

    @Size(
        max = TAMANHO_MAX_DESCRICAO_FATO,
        message = "planinha.valid.fato.descricao.max"
    )
    @NotBlank(message = "planinha.valid.fato.descricao.sem")
    private String descricao;

    @Override
    public String toString() {
        return "[Fato:" + dia + "|" + descricao + "]";
    }
}
