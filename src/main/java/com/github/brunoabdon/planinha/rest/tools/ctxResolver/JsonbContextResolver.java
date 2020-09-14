package com.github.brunoabdon.planinha.rest.tools.ctxResolver;

import static org.jboss.logging.Logger.Level.INFO;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.github.brunoabdon.planinha.modelo.Periodo;

@Provider
public class JsonbContextResolver implements ContextResolver<Jsonb> {

    @Inject
    Logger logger;

    @Inject
    JsonbAdapter<Periodo, ?> periodoJsonAdapter;

    @Override
    public Jsonb getContext(final Class<?> type) {

        logger.logv(INFO, "Resolvendo contexto pra Jsonb.");

        final JsonbConfig config = new JsonbConfig();

        logger.logv(
            INFO, "Registrando adapter de Periodo {0}.",periodoJsonAdapter
        );

        config.withAdapters(periodoJsonAdapter);

        return JsonbBuilder.create(config);
   }
}
