package com.demo.portailsaisie.backend.utils.exception;

public enum ProcessErrorEnum {

    UNAUTHORIZED("user.unauthorized", 401),
    FORBIDDEN("user.forbidden", 403),
    BAD_REQUEST("bad.request", 400),
    BAD_FILE("bad.file", 400),
    BAD_FILE_DATA("bad.file.data", 400),
    BAD_FILE_TYPE("bad.file.type", 400),
    NOT_FOUND("not.found", 404),
    ORDER_LINE_NOT_FOUND("order.line.not.found", 404),
    SALE_SITE_NOT_FOUND("sale.site.not.found", 404),
    SERVER_ERROR("server.error", 500),
    FILE_IO_ERROR("file.io.error", 500),
    TECHNICAL_ERROR("technical.error", 400),
    DATA_NOT_FOUND("not.found.error", 404),
    INVALID_NUMBER_FORMAT("invalid.number.format", 400),
    INVALID_DATE_FORMAT("invalid.date.format", 400),
    REQUIRED_FIELD("required.field.empty", 400),
    FIELD_IO_ERROR("file.io.error", 400),
    BAD_AUTH_DATA("bad.username.password", 400);

    private final String key;
    private final int code;

    ProcessErrorEnum(String key, int code) {
        this.key = key;
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public int getCode() {
        return code;
    }
}
