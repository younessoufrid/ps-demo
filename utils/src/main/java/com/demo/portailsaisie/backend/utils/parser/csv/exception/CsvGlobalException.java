package com.demo.portailsaisie.backend.utils.parser.csv.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsvGlobalException extends RuntimeException {
    private long lineNumber = -1L;

    public CsvGlobalException() {
    }

    public CsvGlobalException(String message) {
        super(message);
    }

    public CsvGlobalException(String message, long lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

    public CsvGlobalException(String message, long lineNumber, String... args) {
        super(message);
        this.lineNumber = lineNumber;
    }

}
