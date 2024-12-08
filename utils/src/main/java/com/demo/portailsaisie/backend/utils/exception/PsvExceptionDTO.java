package com.demo.portailsaisie.backend.utils.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PsvExceptionDTO implements Serializable {

    private int code;

    private String key;

    private String message;

    private double timestamp;

    private String type;

    private Object[] details;

    public PsvExceptionDTO() {
        //default constructor
    }

    public PsvExceptionDTO(int code) {
        this.code = code;
    }

    public PsvExceptionDTO(int code, String key, String message, String type) {
        this.code = code;
        this.key = key;
        this.message = message;
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }

    public PsvExceptionDTO(int code, String key, String message, double timestamp, String type) {
        this.code = code;
        this.key = key;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }

    public PsvExceptionDTO(int code, String key, String message, double timestamp, String type, Object[] details) {
        this.code = code;
        this.key = key;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
        this.details = details;
    }
}
