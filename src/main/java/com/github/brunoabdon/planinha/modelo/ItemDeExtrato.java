package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * TODO documentaçaõ aqui.
 *
 * <p>Obs: Usa o {@linkplain #equals(Object) equals(..)} e {@linkplain
 * #hashCode() hashcode()} de {@link Object}, já que duas instâncias de {@link
 * ItemDeExtrato} para a mesma {@linkplain #getOperacao()} operacao} com um
 * mesmo {@linkplain #getValor() valor} não são necessáriamente a mesma entidade
 * (podem ser de {@linkplain Extrato extratos} distintos).</p>
 *
 * @author bruno
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemDeExtrato implements Serializable{

    private static final long serialVersionUID = 7915709116381425166L;

    private Operacao operacao;

    private int valor;

}