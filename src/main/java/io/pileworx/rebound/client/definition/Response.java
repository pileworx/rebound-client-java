package io.pileworx.rebound.client.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @JsonProperty("status")
    private final int status;
    @JsonProperty("headers")
    private final List<Header> headers;
    @JsonProperty("body")
    private final String body;
    @JsonProperty("values")
    private final Map<String, Object> values;

    @JsonCreator
    public Response(@JsonProperty("status") int status,
                    @JsonProperty("headers") List<Header> headers,
                    @JsonProperty("body") String body,
                    @JsonProperty("values") Map<String, Object> values) {
        this.status = status;
        this.headers = headers;
        this.body = body;
        this.values = values;
    }
}