package io.pileworx.rebound.client.unirest;

public class JsonSerializationException extends RuntimeException {
    public JsonSerializationException(String msg, Exception ex) {
        super(msg, ex);
    }
}
