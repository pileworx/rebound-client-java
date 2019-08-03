package io.pileworx.rebound.client.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {

    @JsonProperty("method")
    private final Method method;
    @JsonProperty("path")
    private final String path;
    @JsonProperty("query")
    private final String query;
    @JsonProperty("headers")
    private final List<Header> headers;
    @JsonProperty("body")
    private final String body;

    @JsonCreator
    public Request(@JsonProperty("method") Method method,
                   @JsonProperty("path") String path,
                   @JsonProperty("query") String query,
                   @JsonProperty("headers") List<Header> headers,
                   @JsonProperty("body") String body) {
        this.method = method;
        this.path = path;
        this.query = query;
        this.headers = headers;
        this.body = body;
    }
}