package com.demo.portailsaisie.backend.utils.parser;

import java.util.Date;

public interface Record {
    default String getString(String field) {
        return getString(field, false);
    }
    String getString(String field, boolean isRequired);
    Long getLong(String field, boolean isRequired);
    Double getDouble(String field, boolean isRequired);
    Integer getInt(String field, boolean isRequired);
    Date getDate(String field, boolean isRequired);
    Float getFloat(String field, boolean isRequired);
}
