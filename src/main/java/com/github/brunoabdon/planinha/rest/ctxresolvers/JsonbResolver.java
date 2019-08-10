package com.github.brunoabdon.planinha.rest.ctxresolvers;

import static org.jboss.logging.Logger.Level.INFO;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.github.brunoabdon.planinha.rest.adapter.LancamentoAdapter;

@Provider
public class JsonbResolver implements ContextResolver<Jsonb> {

	@Inject
	private Logger LOGGER;
	
	@Override
	public Jsonb getContext(final Class<?> type) {
		LOGGER.log(INFO, "Criando Jsonb.");
		final JsonbConfig config = new JsonbConfig();
        config.withAdapters(new LancamentoAdapter());
        return JsonbBuilder.create(config);	
    }

}
