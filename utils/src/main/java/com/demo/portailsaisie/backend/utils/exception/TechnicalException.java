package com.demo.portailsaisie.backend.utils.exception;

import lombok.Getter;

@Getter
public class TechnicalException extends RuntimeException {

    private final long timestamp;

    public TechnicalException(String message) {
        super(message);
        this.timestamp = System.currentTimeMillis();
    }

    public TechnicalException(String message, Exception source) {
        super(message, source);
        this.timestamp = System.currentTimeMillis();
    }

    public TechnicalException(Exception source) {
        super(source.getMessage(), source);
        this.timestamp = System.currentTimeMillis();
    }
}
