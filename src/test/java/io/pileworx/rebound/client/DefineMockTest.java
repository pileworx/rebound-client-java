package io.pileworx.rebound.client;

import io.pileworx.rebound.client.builder.DefineRequest;
import io.pileworx.rebound.client.builder.DefineResponse;
import io.pileworx.rebound.client.builder.exception.BodyReadException;
import io.pileworx.rebound.client.definition.Header;
import io.pileworx.rebound.client.definition.Method;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.pileworx.rebound.client.definition.Method.GET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DefineMockTest {

    private static final String SCENARIO = "Test Scenario";
    private static final Method METHOD = GET;
    private static final String PATH = "/foo";
    private static final int STATUS = 200;
    private static final String QUERY_STRING = "foo=bar&bar=baz";
    private static final String REQ_BODY = "{\"foo\":\"bar\"}";
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_VALUE = "application/hal+json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String RESP_BODY_LITERAL = "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]";
    private static final String RESP_BODY_PATH = "./src/test/java/io/pileworx/rebound/client/velocity.vm";
    private static final Map<String, Object> VALUES = new HashMap<>();

    @Test
    public void canBuild() {
        var mock = new DefineMock().with(m -> {
            m.scenario = SCENARIO;
            m.when = new DefineRequest().with(req -> {
                req.method = METHOD;
                req.path = PATH;
                req.query = QUERY_STRING;
                req.headers = Header.of​(ACCEPT, ACCEPT_VALUE);
                req.body = REQ_BODY;
            }).build();
            m.then = List.of(
                    new DefineResponse().with(resp1 -> {
                        resp1.status = STATUS;
                        resp1.headers = Header.of​(CONTENT_TYPE, CONTENT_TYPE_VALUE);
                        resp1.body = RESP_BODY_LITERAL;
                    }).build(),
                    new DefineResponse().with(resp2 -> {
                        resp2.status = STATUS;
                        resp2.headers = Header.of​(CONTENT_TYPE, CONTENT_TYPE_VALUE);
                        resp2.bodyFromFile = Paths.get(RESP_BODY_PATH);
                        resp2.values = VALUES;
                    }).build()
            );
        }).build();

        assertThat(mock.getScenario(), is(equalTo(SCENARIO)));

        assertThat(mock.getWhen().getRequest().getMethod(), is(equalTo(METHOD)));
        assertThat(mock.getWhen().getRequest().getPath(), is(equalTo(PATH)));
        assertThat(mock.getWhen().getRequest().getQuery(), is(equalTo(QUERY_STRING)));
        assertThat(mock.getWhen().getRequest().getHeaders().get(0).getName(), is(equalTo(ACCEPT)));
        assertThat(mock.getWhen().getRequest().getHeaders().get(0).getValue(), is(equalTo(ACCEPT_VALUE)));
        assertThat(mock.getWhen().getRequest().getBody(), is(equalTo(REQ_BODY)));

        assertThat(mock.getThen().get(0).getStatus(), is(equalTo(STATUS)));
        assertThat(mock.getThen().get(0).getHeaders().get(0).getName(), is(equalTo(CONTENT_TYPE)));
        assertThat(mock.getThen().get(0).getHeaders().get(0).getValue(), is(equalTo(CONTENT_TYPE_VALUE)));
        assertThat(mock.getThen().get(0).getBody(), is(equalTo(RESP_BODY_LITERAL)));

        assertThat(mock.getThen().get(1).getStatus(), is(equalTo(STATUS)));
        assertThat(mock.getThen().get(1).getHeaders().get(0).getName(), is(equalTo(CONTENT_TYPE)));
        assertThat(mock.getThen().get(1).getHeaders().get(0).getValue(), is(equalTo(CONTENT_TYPE_VALUE)));
        assertThat(mock.getThen().get(1).getBody(), is(not(nullValue())));
        assertThat(mock.getThen().get(1).getValues(), is(equalTo(VALUES)));
    }

    @Test(expected = BodyReadException.class)
    public void throwsExceptionIfBodyFileIsNotFound() {
        new DefineMock().with(m -> {
            m.scenario = SCENARIO;
            m.when = new DefineRequest().build();
            m.then = List.of(
                    new DefineResponse().with(resp2 -> {
                        resp2.status = STATUS;
                        resp2.headers = Header.of​(CONTENT_TYPE, CONTENT_TYPE_VALUE);
                        resp2.bodyFromFile = Paths.get("./src/test/java/io/pileworx/rebound/client/no_velocity.vm");
                        resp2.values = VALUES;
                    }).build()
            );
        }).build();
    }
}
