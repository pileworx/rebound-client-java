package io.pileworx.rebound.client;

import io.pileworx.rebound.client.definition.Mock;
import io.pileworx.rebound.client.unirest.UnirestClient;

public class ReboundRestClient {

    private final String reboundHost;
    private final UnirestClient unirestClient;

    public static ReboundRestClient create(String reboundHost) {
        return new ReboundRestClient(reboundHost, new UnirestClient());
    }

    ReboundRestClient(String reboundHost, UnirestClient unirestClient) {
        this.reboundHost = reboundHost;
        this.unirestClient = unirestClient;
    }

    public Status createMock(Mock mock) {
        return unirestClient.execMock(reboundHost, mock);
    }

    public Status clearMocks() {
        return unirestClient.clearMocks(reboundHost);
    }
}