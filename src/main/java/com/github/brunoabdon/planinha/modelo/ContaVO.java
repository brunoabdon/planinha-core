package com.github.brunoabdon.planinha.modelo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.github.brunoabdon.commons.modelo.IdentifiableBaseInt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Uma carteira à qual se pode associar e movimentar um valor.
 *
 * @author bruno
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContaVO extends IdentifiableBaseInt implements Serializable {

    private static final long serialVersionUID = 7321886996603362113L;

    private static final int TAMANHO_MAX_NOME = 70;

    @NotEmpty
    @NotBlank
    @Size(max = TAMANHO_MAX_NOME)
    @EqualsAndHashCode.Exclude
    private String nome;

    public ContaVO(final Integer id) {
        this(id,null);
    }

    public ContaVO(final Integer id, final String nome) {
        super(id);
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "[Conta:"+getId()+"|" + nome + "]";
    }
}