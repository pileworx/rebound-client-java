package io.pileworx.rebound.client;

import io.pileworx.rebound.client.builder.DefineRequest;
import io.pileworx.rebound.client.builder.DefineResponse;
import io.pileworx.rebound.client.definition.Header;
import io.pileworx.rebound.client.definition.Method;
import kong.unirest.Unirest;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Ignore
public class IntegrationTest {

    private final ReboundRestClient client = ReboundRestClient.create("http://localhost:8585");

    @Test
    public void canSendAndRetrieveMocks() {
        var mock = new DefineMock().with(m -> {
            m.scenario = "Test Scenario";
            m.when = new DefineRequest().with(req -> {
                req.method = Method.GET;
                req.path = "/foo";
                req.query = "foo=bar&bar=baz";
                req.headers = Header.of​("Accept", "application/hal+json");
            }).build();
            m.then = List.of(
                    new DefineResponse().with(resp1 -> {
                        resp1.status = 200;
                        resp1.headers = Header.of​("Content-Type", "application/hal+json");
                        resp1.body = "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]";
                    }).build(),
                    new DefineResponse().with(resp2 -> {
                        resp2.status = 200;
                        resp2.headers = Header.of​("Content-Type", "application/hal+json");
                        resp2.bodyFromFile = Paths.get("./src/test/java/io/pileworx/rebound/client/velocity.vm");
                        resp2.values = Map.of("myValue", "this is my value");
                    }).build()
            );
        }).build();

        client.createMock(mock);

        var response = Unirest
                .get("http://localhost:8585/foo?foo=bar&bar=baz")
                .header("Accept", "application/hal+json")
                .asString();

        assertThat(response.getStatus(), is(equalTo(200)));
    }

    @Test
    public void canClearMocks() {
        client.clearMocks();

        var response = Unirest
                .get("http://localhost:8585/foo?foo=bar&bar=baz")
                .header("Accept", "application/hal+json")
                .asString();

        assertThat(response.getStatus(), is(equalTo(400)));
    }
}