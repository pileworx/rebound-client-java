package io.pileworx.rebound.client.unirest;

import io.pileworx.rebound.client.DefineMockCmd;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UnirestClientTest {

    private static final Map<String, String> values = new HashMap<>();

    static {
        values.put("myProperty", "my value");
    }

    private static final DefineMockCmd cmd = new DefineMockCmd(
            "POST",
            "/foo",
            "foo=bar&bar=baz",
            200,
            "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]",
            "application/hal+json",
            values);

    private static final String json = "{\"method\":\"POST\",\"path\":\"/foo\",\"qs\":\"foo=bar&bar=baz\",\"status\":200,\"response\":\"[#foreach($i in [1..5]){\\\"propertyName\\\":\\\"this is my value\\\"} #if($foreach.count != 5), #end #end]\",\"contentType\":\"application/hal+json\",\"values\":{\"myProperty\":\"my value\"}}";

    @Test
    public void canSerialize() {
        String serialized = UnirestClient.mapper.writeValue(cmd);

        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void canDeserialize() {
        DefineMockCmd deserialized = UnirestClient.mapper.readValue(json, DefineMockCmd.class);

        assertThat(deserialized, is(samePropertyValuesAs(cmd)));
    }
}
