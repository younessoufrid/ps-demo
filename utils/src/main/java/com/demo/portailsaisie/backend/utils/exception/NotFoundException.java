package com.demo.portailsaisie.backend.utils.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final long timestamp;

    public NotFoundException(String message) {
        super(message);
        this.timestamp = System.currentTimeMillis();
    }

    public NotFoundException(String message, Exception source) {
        super(message, source);
        this.timestamp = System.currentTimeMillis();
    }

    public NotFoundException(Exception source) {
        super(source.getMessage(), source);
        this.timestamp = System.currentTimeMillis();
    }
}
