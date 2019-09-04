package io.pileworx.rebound.client.builder;

import io.pileworx.rebound.client.builder.exception.BodyReadException;
import io.pileworx.rebound.client.definition.Header;
import io.pileworx.rebound.client.definition.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DefineResponse {
    public  int status;
    public List<Header> headers;
    public String body;
    public Path bodyFromFile;
    public Map<String, Object> values;

    public DefineResponse with(Consumer<DefineResponse> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Response build() {
        return new Response(status, headers, getBody(), values);
    }

    private String getBody() {
        try {
            return null != bodyFromFile ? new String(Files.readAllBytes(bodyFromFile)) : body;
        } catch (IOException e) {
            throw new BodyReadException("Failed to load body from file.", e);
        }
    }
}