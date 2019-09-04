package io.pileworx.rebound.client;

import io.pileworx.rebound.client.definition.Mock;
import io.pileworx.rebound.client.definition.Request;
import io.pileworx.rebound.client.definition.Response;
import io.pileworx.rebound.client.definition.When;

import java.util.List;
import java.util.function.Consumer;

public class DefineMock {

    public String scenario;
    public Request when;
    public List<Response> then;

    public DefineMock with(Consumer<DefineMock> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Mock build() {
        return new Mock(scenario, new When(when), then);
    }
}