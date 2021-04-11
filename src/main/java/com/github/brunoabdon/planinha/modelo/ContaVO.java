package com.github.brunoabdon.planinha.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.github.brunoabdon.commons.modelo.EntidadeBaseInt;


/**
 * Uma carteira à qual se pode associar e movimentar um valor.
 *
 * @author bruno
 */
public class ContaVO extends EntidadeBaseInt {

    private static final long serialVersionUID = 7321886996603362113L;

    private static final int TAMANHO_MAX_NOME = 70;

    @NotEmpty
    @NotBlank
    @Size(max = TAMANHO_MAX_NOME)
    private String nome;

    public ContaVO() {
    }

    public ContaVO(final Integer id) {
        this(id,null);
    }

    public ContaVO(final String nome) {
        this.nome = nome;
    }

    public ContaVO(final Integer id, final String nome) {
        this(nome);
        super.setId(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public static ContaVO fromString(final String str){
        return EntidadeBaseInt.fromString(ContaVO.class, str);
    }

    @Override
    public String toString() {
        return "[Conta:"+getId()+"|" + nome + "]";
    }
}