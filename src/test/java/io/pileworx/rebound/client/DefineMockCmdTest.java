package io.pileworx.rebound.client;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DefineMockCmdTest {

    private static final String METHOD = "POST";
    private static final String PATH = "/foo";
    private static final int STATUS = 200;
    private static final String QUERY_STRING = "foo=bar&bar=baz";
    private static final String CONTENT_TYPE = "application/hal+json";
    private static final String RESPONSE_LITERAL = "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]";
    private static final Map<String, String> VALUES = new HashMap<>();

    @Test
    public void canConstructAndAccessGetters() {
        DefineMockCmd cmd = new DefineMockCmd(
                METHOD,
                PATH,
                QUERY_STRING,
                STATUS,
                RESPONSE_LITERAL,
                CONTENT_TYPE,
                VALUES);

        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(equalTo(QUERY_STRING)));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), is(equalTo(RESPONSE_LITERAL)));
        assertThat(cmd.getValues(), is(equalTo(VALUES)));
    }
}