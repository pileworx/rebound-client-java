package io.pileworx.rebound.client.definition;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mock {

    @JsonProperty("scenario")
    private final String scenario;
    @JsonProperty("when")
    private final When when;
    @JsonProperty("then")
    private final List<Response> then;

    @JsonCreator
    public Mock(@JsonProperty("scenario") String scenario,
                @JsonProperty("when") When when,
                @JsonProperty("then") List<Response> then) {
        this.scenario = scenario;
        this.when = when;
        this.then = then;
    }
}