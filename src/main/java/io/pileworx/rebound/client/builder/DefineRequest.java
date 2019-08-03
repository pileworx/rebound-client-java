package io.pileworx.rebound.client.builder;

import io.pileworx.rebound.client.definition.Header;
import io.pileworx.rebound.client.definition.Method;
import io.pileworx.rebound.client.definition.Request;

import java.util.List;
import java.util.function.Consumer;

public class DefineRequest {
    public Method method;
    public String path;
    public String query;
    public List<Header> headers;
    public String body;

    public DefineRequest with(Consumer<DefineRequest> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Request build() {
        return new Request(method, path, query, headers, body);
    }
}