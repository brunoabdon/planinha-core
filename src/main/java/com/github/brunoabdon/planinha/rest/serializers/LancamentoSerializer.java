package com.github.brunoabdon.planinha.rest.serializers;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

import com.github.brunoabdon.gastoso.Lancamento;

public class LancamentoSerializer implements JsonbSerializer<Lancamento> {

	@Override
	public void serialize(
			final Lancamento lancamento, 
			final JsonGenerator gen, 
			final SerializationContext ctx) {
	
		gen.writeStartObject();
		ctx.serialize("conta", lancamento.getConta(), gen);
		gen.write("valor", lancamento.getValor());
		gen.writeEnd();
	}
}
