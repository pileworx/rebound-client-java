package io.pileworx.rebound.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefineMockCmd {

    @JsonProperty("method")
    private final String method;
    @JsonProperty("path")
    private final String path;
    @JsonProperty("qs")
    private final String qs;
    @JsonProperty("status")
    private final int status;
    @JsonProperty("response")
    private final String response;
    @JsonProperty("contentType")
    private final String contentType;
    @JsonProperty("values")
    private final Map<String, String> values;

    public DefineMockCmd(@JsonProperty("method") String method,
                         @JsonProperty("path") String path,
                         @JsonProperty("qs") String qs,
                         @JsonProperty("status") int status,
                         @JsonProperty("response") String response,
                         @JsonProperty("contentType") String contentType,
                         @JsonProperty("values")  Map<String, String> values) {
        this.method = method;
        this.path = path;
        this.qs = qs;
        this.status = status;
        this.response = response;
        this.contentType = contentType;
        this.values = values;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQs() {
        return qs;
    }

    public int getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
