package com.demo.portailsaisie.backend.utils.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface FileParser {
    <T> List<T> parseEntries(InputStream inputStream, Class<T> clazz) throws IOException;
    <T> void writeEntries(OutputStream outputStream, List<T> entries, Class<T> clazz) throws IOException;
}
