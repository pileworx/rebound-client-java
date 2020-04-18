package io.pileworx.rebound.client.unirest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.pileworx.rebound.client.definition.Mock;
import io.pileworx.rebound.client.Status;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import java.io.IOException;

public class UnirestClient {

    private static final String ACCEPT = "Accept";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String URI_PATH = "mock";
    private static final String STATUS_FAILED = "FAILED";

    protected static final ObjectMapper mapper = new ObjectMapper() {
        private final com.fasterxml.jackson.databind.ObjectMapper jObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        @Override
        public <T> T readValue(String value, Class<T> valueType) {
            try {
                return jObjectMapper.readValue(value, valueType);
            } catch (IOException e) {
                throw new JsonSerializationException("Failed to deserialize object", e);
            }
        }

        @Override
        public String writeValue(Object value) {
            try {
                return jObjectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                throw new JsonSerializationException("Failed to serialize object", e);
            }
        }
    };

    public UnirestClient() {
        Unirest.config().setObjectMapper(mapper);
    }

    public Status execMock(String reboundHost, Mock cmd) {
        try {
            return Unirest.put(String.format("%s/%s", reboundHost, URI_PATH))
                    .header(ACCEPT, APPLICATION_JSON)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .body(cmd)
                    .asObject(Status.class)
                    .getBody();
        } catch (UnirestException e) {
            return new Status(STATUS_FAILED, e.getMessage());
        }
    }

    public Status clearMocks(String reboundHost) {
        try {
            return Unirest.delete(String.format("%s/%s", reboundHost, URI_PATH))
                    .header(ACCEPT, APPLICATION_JSON)
                    .asObject(Status.class)
                    .getBody();
        } catch (UnirestException e) {
            return new Status(STATUS_FAILED, e.getMessage());
        }
    }
}
