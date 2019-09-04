package io.pileworx.rebound.client.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class When {

    @JsonProperty("request")
    private final Request request;

    @JsonCreator
    public When(@JsonProperty("request") Request request) {
        this.request = request;
    }
}