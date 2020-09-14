package com.github.brunoabdon.planinha.rest.toos.adapters;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;

import com.github.brunoabdon.planinha.modelo.Periodo;

@ApplicationScoped
public class PeriodoJsonbAdapter implements JsonbAdapter<Periodo, JsonString> {

    @Override
    public JsonString adaptToJson(final Periodo periodo) {
        return Json.createValue(periodo.serialize());
    }

    @Override
    public Periodo adaptFromJson(final JsonString jsonString) {
        return Periodo.fromString(jsonString.getString());
    }

    @Override
    public String toString() {
        return "[PeriodoJsonbAdapter#" + super.hashCode() + "]";
    }

}
