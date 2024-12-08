package com.demo.portailsaisie.backend.utils.exception;

public interface ProcessExceptionFormat {

    int getHttpStatusCode();

    String getKey();

    String getMessage();

    Long getTimestamp();
}
