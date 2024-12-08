package com.demo.portailsaisie.backend.utils.parser;

import java.util.List;

@FunctionalInterface
public interface RecordProcessor {
    <T extends Record, K> List<K> processRecord(T record, Class<K> clazz);
}
