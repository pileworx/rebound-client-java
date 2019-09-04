package io.pileworx.rebound.client.builder.exception;

public class BodyReadException extends RuntimeException {
    public BodyReadException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
