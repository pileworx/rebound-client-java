package io.pileworx.rebound.client;

import io.pileworx.rebound.client.unirest.UnirestClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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

    public Status createMock(String method, String path, int status, String contentType) {
        return createMock(method, path, status, contentType, null);
    }

    public Status createMock(String method, String path, int status, String contentType, String queryString) {
        return createMock(method, path, status, contentType, queryString, (String)null, null);
    }

    public Status createMock(String method, String path, int status, String contentType, String response, Map<String, String> values) {
        return createMock(method, path, status, contentType, null, response, values);
    }

    public Status createMock(String method, String path, int status, String contentType, Path response, Map<String, String> values) {
        return createMock(method, path, status, contentType, null, response, values);

    }

    public Status createMock(String method, String path, int status, String contentType, String queryString, Path response, Map<String, String> values) {
        try {
            return createMock(method, path, status, contentType, queryString, responseFromFile(response), values);
        } catch (IOException ioex) {
            return new Status("FAILED", "Failed to read file form " + response.toString());
        }
    }

    public Status createMock(String method, String path, int status, String contentType, String queryString, String response, Map<String, String> values) {
        return unirestClient.execMock(
                reboundHost,
                new DefineMockCmd(method, path, queryString, status, response, contentType, values));
    }

    public Status clearMocks() {
        return unirestClient.clearMocks(reboundHost);
    }

    private String responseFromFile(Path responseFile) throws IOException {
        return new String(Files.readAllBytes(responseFile));
    }
}