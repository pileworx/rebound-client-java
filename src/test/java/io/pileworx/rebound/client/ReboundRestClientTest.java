package io.pileworx.rebound.client;

import io.pileworx.rebound.client.unirest.UnirestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReboundRestClientTest {

    private static final String METHOD = "POST";
    private static final String PATH = "/foo";
    private static final int STATUS = 200;
    private static final String QUERY_STRING = "foo=bar&bar=baz";
    private static final String CONTENT_TYPE = "application/hal+json";
    private static final Path RESPONSE_BY_PATH = Paths.get("./src/test/java/io/pileworx/rebound/client/velocity.vm");
    private static final String RESPONSE_LITERAL = "[#foreach($i in [1..5]){\"propertyName\":\"this is my value\"} #if($foreach.count != 5), #end #end]";
    private static final Map<String, String> VALUES = new HashMap<>();
    private static final Status RESPONSE_STATUS = new Status("foo", "bar");
    private static final String REBOUND_HOST = "http://localhost:8585";
    @Mock
    private UnirestClient unirestClient;

    private ReboundRestClient reboundClient;

    @Before
    public void setup() {
        reboundClient = new ReboundRestClient(REBOUND_HOST, unirestClient);
    }

    @Test
    public void createMockWithAllParametersAndPathToResponseShouldCallClientWithCompleteCommand() {

        ArgumentCaptor<DefineMockCmd> captor = ArgumentCaptor.forClass(DefineMockCmd.class);
        when(unirestClient.execMock(eq(REBOUND_HOST), any(DefineMockCmd.class))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, QUERY_STRING, RESPONSE_BY_PATH, VALUES);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        DefineMockCmd cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(equalTo(QUERY_STRING)));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), containsString("#foreach($i in [1..5])"));
        assertThat(cmd.getValues(), is(equalTo(VALUES)));
    }

    @Test
    public void createMockWithInvalidPathToResponseShouldReturnFailedStatus() {
        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, QUERY_STRING, Paths.get("./invalid"), VALUES);

        assertThat(rStatus.getStatus(), is(equalTo("FAILED")));
    }

    @Test
    public void createMockWithNoQueryStringAndPathToResponseShouldCallClientWithMatchingCommand() {

        ArgumentCaptor<DefineMockCmd> captor = ArgumentCaptor.forClass(DefineMockCmd.class);
        when(unirestClient.execMock(eq(REBOUND_HOST), any(DefineMockCmd.class))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, RESPONSE_BY_PATH, VALUES);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        DefineMockCmd cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(nullValue()));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), containsString("#foreach($i in [1..5])"));
        assertThat(cmd.getValues(), is(equalTo(VALUES)));
    }

    @Test
    public void createMockWithAllParametersAndStringResponseShouldCallClientWithCompleteCommand() {
        ArgumentCaptor<DefineMockCmd> captor = ArgumentCaptor.forClass(DefineMockCmd.class);
        when(unirestClient.execMock(eq(REBOUND_HOST), any(DefineMockCmd.class))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, QUERY_STRING, RESPONSE_LITERAL, VALUES);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        DefineMockCmd cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(equalTo(QUERY_STRING)));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), is(equalTo(RESPONSE_LITERAL)));
        assertThat(cmd.getValues(), is(equalTo(VALUES)));
    }

    @Test
    public void createMockWithNoQueryStringAndStringResponseShouldCallClientWithMatchingCommand() {
        ArgumentCaptor<DefineMockCmd> captor = ArgumentCaptor.forClass(DefineMockCmd.class);
        when(unirestClient.execMock(eq(REBOUND_HOST), any(DefineMockCmd.class))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, RESPONSE_LITERAL, VALUES);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        DefineMockCmd cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(nullValue()));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), is(equalTo(RESPONSE_LITERAL)));
        assertThat(cmd.getValues(), is(equalTo(VALUES)));
    }

    @Test
    public void createMockWithRequiredParamsAndQueryStringShouldCallClientWithMatchingCommand() {
        ArgumentCaptor<DefineMockCmd> captor = ArgumentCaptor.forClass(DefineMockCmd.class);
        when(unirestClient.execMock(eq(REBOUND_HOST), any(DefineMockCmd.class))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE, QUERY_STRING);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        DefineMockCmd cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(equalTo(QUERY_STRING)));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), is(nullValue()));
        assertThat(cmd.getValues(), is(nullValue()));
    }

    @Test
    public void createMockWithRequiredParamsShouldCallClientWithMatchingCommand() {
        ArgumentCaptor<DefineMockCmd> captor = ArgumentCaptor.forClass(DefineMockCmd.class);
        when(unirestClient.execMock(eq(REBOUND_HOST), any(DefineMockCmd.class))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(METHOD, PATH, STATUS, CONTENT_TYPE);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        DefineMockCmd cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd.getMethod(), is(equalTo(METHOD)));
        assertThat(cmd.getPath(), is(equalTo(PATH)));
        assertThat(cmd.getStatus(), is(equalTo(STATUS)));
        assertThat(cmd.getQs(), is(nullValue()));
        assertThat(cmd.getContentType(), is(equalTo(CONTENT_TYPE)));
        assertThat(cmd.getResponse(), is(nullValue()));
        assertThat(cmd.getValues(), is(nullValue()));
    }

    @Test
    public void createShouldReturnInstanceOfClient() {
        ReboundRestClient client = ReboundRestClient.create(REBOUND_HOST);

        assertThat(client, is(instanceOf(ReboundRestClient.class)));
    }

    @Test
    public void clearShouldCallClearOnClient() {
        when(unirestClient.clearMocks(eq(REBOUND_HOST))).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.clearMocks();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
    }
}
