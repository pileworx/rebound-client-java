package io.pileworx.rebound.client.unirest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.pileworx.rebound.client.DefineMockCmd;
import io.pileworx.rebound.client.Status;

import java.io.IOException;

public class UnirestClient {

    protected static final ObjectMapper mapper = new ObjectMapper() {
        private com.fasterxml.jackson.databind.ObjectMapper jObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

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
        Unirest.setObjectMapper(mapper);
    }

    public Status execMock(String reboundHost, DefineMockCmd cmd) {
        try {
            return Unirest.put(reboundHost + "/mock")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(cmd)
                    .asObject(Status.class).getBody();

        } catch (UnirestException e) {
            return new Status("FAILED", e.getMessage());
        }
    }

    public Status clearMocks(String reboundHost) {
        try {
            return Unirest.delete(reboundHost + "/mock")
                    .header("Accept", "application/json")
                    .asObject(Status.class).getBody();

        } catch (UnirestException e) {
            return new Status("FAILED", e.getMessage());
        }
    }
}
