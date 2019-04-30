package io.pileworx.rebound.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {

    @JsonProperty("status")
    private final String status;
    @JsonProperty("message")
    private final String message;

    @JsonCreator
    public Status(@JsonProperty("status") String status,
                  @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
