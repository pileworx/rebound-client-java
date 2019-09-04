package io.pileworx.rebound.client.unirest;

import io.pileworx.rebound.client.DefineMock;
import io.pileworx.rebound.client.builder.DefineRequest;
import io.pileworx.rebound.client.builder.DefineResponse;
import io.pileworx.rebound.client.definition.Header;
import io.pileworx.rebound.client.definition.Mock;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.pileworx.rebound.client.definition.Method.GET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UnirestClientTest {

    private static final Map<String, Object> values = new HashMap<>();

    static {
        values.put("myProperty", "my value");
    }

    private static final Mock cmd = new DefineMock().with(m -> {
        m.scenario = "Test Scenario";
        m.when = new DefineRequest().with(req -> {
            req.method = GET;
            req.path = "/foo";
            req.query = "foo=bar&bar=baz";
            req.headers = Header.of("Accept", "application/hal+json");
            req.body = "{\"foo\":\"bar\"}";
        }).build();
        m.then = List.of(
                new DefineResponse().with(resp -> {
                    resp.status = 200;
                    resp.headers = Header.of("Content-Type", "application/json");
                    resp.body = "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]";
                    resp.values = values;
                }).build()
        );
    }).build();

    private static final String json = "{\"scenario\":\"Test Scenario\",\"when\":{\"request\":{\"method\":\"GET\",\"path\":\"/foo\",\"query\":\"foo=bar&bar=baz\",\"headers\":[{\"name\":\"Accept\",\"value\":\"application/hal+json\"}],\"body\":\"{\\\"foo\\\":\\\"bar\\\"}\"}},\"then\":[{\"status\":200,\"headers\":[{\"name\":\"Content-Type\",\"value\":\"application/json\"}],\"body\":\"[#foreach($i in [1..5]){\\\"propertyName\\\":\\\"this is my value\\\"} #if($foreach.count != 5), #end #end]\",\"values\":{\"myProperty\":\"my value\"}}]}";

    @Test
    public void canSerialize() {
        var serialized = UnirestClient.mapper.writeValue(cmd);

        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void canDeserialize() {
        var deserialized = UnirestClient.mapper.readValue(json, Mock.class);

        assertThat(deserialized.equals(cmd), is(true));
        assertThat(deserialized, is(samePropertyValuesAs(cmd)));
    }
}
