package io.pileworx.rebound.client.unirest;

import org.junit.Test;

public class JsonSerializationExceptionTest {

    @Test(expected = JsonSerializationException.class)
    public void shouldThrow() {
        throw new JsonSerializationException("", new RuntimeException());
    }
}
