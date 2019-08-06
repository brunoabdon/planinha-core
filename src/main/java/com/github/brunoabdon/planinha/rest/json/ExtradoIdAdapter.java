package com.github.brunoabdon.planinha.rest.json;

import static java.time.LocalDate.parse;
import static java.time.Period.between;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.bind.adapter.JsonbAdapter;

import com.github.brunoabdon.commons.util.modelo.Periodo;
import com.github.brunoabdon.gastoso.Conta;
import com.github.brunoabdon.planinha.Extrato;
import com.github.brunoabdon.planinha.Extrato.Id;

public class ExtradoIdAdapter implements JsonbAdapter<Extrato.Id, String>{

    
    public static final Pattern EXTRADO_ID_REGEXP =
        Pattern.compile("^\\d+-\\d+(-(\\d){2}){2}-\\d+");
    
    /**
     * Formata um {@link Id} como a string {@code ccc-YYYY-MM-DD-dd} onde:
     * <ul>
     *   <li><em>ccc</em> É o id da conta (uma sequencia de N digitos);</li>
     *   <li><em>YYYY-MM-DD</em> é a {@linkplain Id#getPeriodo() data inicial
     *   do id}, no formato {@link DateTimeFormatter#ISO_LOCAL_DATE};</li> 
     *   <li><em>dd</em> é a quantidade de dias entre a data inicio e a data
     *   fim do Id (inclusivamente).;</li> 
     * </ul>
     * 
     * @return O id formatado como uma string.
     */
    @Override
    public String adaptToJson(final Id id) throws Exception {
        
        final Integer idConta = id.getConta().getId();
        
        final Periodo periodo = id.getPeriodo();
        final LocalDate inicio = periodo.getDataMinima();
        final LocalDate fim = periodo.getDataMaxima();
        
        final int quantosDias = between(inicio, fim.plusDays(1)).getDays();
        final String inicioFormatado = inicio.format(ISO_LOCAL_DATE);
        
        return
            new StringBuilder(16)
                .append(idConta)
                .append("-")
                .append(inicioFormatado)
                .append("-")
                .append(quantosDias)
                .toString();
    }

    @Override
    public Id adaptFromJson(final String str) throws Exception {
        final Matcher matcher = EXTRADO_ID_REGEXP.matcher(str);
        
        if(!matcher.matches()) {
            throw new IllegalArgumentException(
                "Não segue " + EXTRADO_ID_REGEXP + ": \"" + str + "\"."
            );
        }
        
        
        final Periodo periodo = getPeriodo(matcher);

        final Conta conta = parseConta(matcher);

        return new Extrato.Id(conta,periodo);
        
    }

    private Periodo getPeriodo(final Matcher matcher) {
        final String strDataInicio = matcher.group(2);
        
        final LocalDate inicio = parseData(strDataInicio);
        
        final String strQuantosDias = matcher.group(3);
        final int quantosDias = 
            parseNumero(strQuantosDias, "uma quantidade de dias");
        
        final LocalDate fim = inicio.plusDays(quantosDias);

        return new Periodo(inicio,fim);
    }

    private LocalDate parseData(final String strData) {
        LocalDate inicio;
        
        try {
            inicio = parse(strData, ISO_LOCAL_DATE);
        } catch (final DateTimeException e) {
            throw new IllegalArgumentException(
                "\"" + strData + "\" não é " + ISO_LOCAL_DATE + ".", e
            );
        }
        return inicio;
    }

    private Conta parseConta(final Matcher matcher) {
        final String strConta = matcher.group(1);
        final int contaId = parseNumero(strConta, "um id de conta");
        return new Conta(contaId);
    }

    int parseNumero(final String strConta, final String desc) {
        final int contaId;
        try {
            contaId = Integer.parseInt(strConta);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(
                "\""+strConta+"\" não parece " + desc + " válido.", e
            );
        }
        return contaId;
    }

}
