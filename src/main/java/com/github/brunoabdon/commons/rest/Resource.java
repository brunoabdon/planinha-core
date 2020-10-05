package com.github.brunoabdon.commons.rest;

import java.net.URI;

import javax.json.bind.annotation.JsonbProperty;

public interface Resource {

    @JsonbProperty("uri")
    public URI getURI();

}
