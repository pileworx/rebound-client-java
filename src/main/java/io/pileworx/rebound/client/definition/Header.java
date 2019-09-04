package io.pileworx.rebound.client.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Header {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("value")
    private final String value;

    public static List<Header> of(String k1, String v1) {
        return List.of(new Header(k1, v1));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2) {
        return List.of(new Header(k1, v1), new Header(k2, v2));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4), new Header(k5, v5));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5, String k6, String v6) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4), new Header(k5, v5), new Header(k6, v6));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5, String k6, String v6, String k7, String v7) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4), new Header(k5, v5), new Header(k6, v6), new Header(k7, v7));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5, String k6, String v6, String k7, String v7, String k8, String v8) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4), new Header(k5, v5), new Header(k6, v6), new Header(k7, v7), new Header(k8, v8));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5, String k6, String v6, String k7, String v7, String k8, String v8, String k9, String v9) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4), new Header(k5, v5), new Header(k6, v6), new Header(k7, v7), new Header(k8, v8), new Header(k9, v9));
    }

    public static List<Header> of(String k1, String v1, String k2, String v2, String k3, String v3, String k4, String v4, String k5, String v5, String k6, String v6, String k7, String v7, String k8, String v8, String k9, String v9, String k10, String v10) {
        return List.of(new Header(k1, v1), new Header(k2, v2), new Header(k3, v3), new Header(k4, v4), new Header(k5, v5), new Header(k6, v6), new Header(k7, v7), new Header(k8, v8), new Header(k9, v9), new Header(k10, v10));
    }

    @JsonCreator
    public Header(@JsonProperty("name") String name,
                  @JsonProperty("value") String value) {
        this.name = name;
        this.value = value;
    }
}
