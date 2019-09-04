package io.pileworx.rebound.client;

import io.pileworx.rebound.client.definition.Mock;
import io.pileworx.rebound.client.unirest.UnirestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReboundRestClientTest {

    private static final Status RESPONSE_STATUS = new Status("foo", "bar");
    private static final String REBOUND_HOST = "http://localhost:8585";
    @org.mockito.Mock
    private UnirestClient unirestClient;
    @org.mockito.Mock
    private Mock mock;


    private ReboundRestClient reboundClient;

    @Before
    public void setup() {
        reboundClient = new ReboundRestClient(REBOUND_HOST, unirestClient);
    }

    @Test
    public void createMockShouldCallUnirestWithMock() {

        ArgumentCaptor<Mock> captor = ArgumentCaptor.forClass(Mock.class);
        when(unirestClient.execMock(REBOUND_HOST, mock)).thenReturn(RESPONSE_STATUS);

        Status rStatus = reboundClient.createMock(mock);

        verify(unirestClient).execMock(eq(REBOUND_HOST), captor.capture());

        Mock cmd = captor.getValue();

        assertThat(rStatus, is(equalTo(RESPONSE_STATUS)));
        assertThat(cmd, is(sameInstance(mock)));
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
