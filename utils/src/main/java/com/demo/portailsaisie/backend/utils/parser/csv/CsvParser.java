package com.demo.portailsaisie.backend.utils.parser.csv;

import com.demo.portailsaisie.backend.utils.parser.FileParser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Setter
@Getter
@Builder
public class CsvParser implements FileParser {
    private char separator = ';';
    private boolean withApplyQuotesToAll = false;
    private Charset encoding = StandardCharsets.ISO_8859_1;

    @Override
    public <T> List<T> parseEntries(InputStream inputStream, Class<T> clazz) {
        log.debug("Started reading rows");
        InputStreamReader reader = new InputStreamReader(inputStream, encoding);
        // Defining CSV file header column translation mapping strategy
        BeanFieldsOrderCustomMappingStrategy<T> strategy =
                new BeanFieldsOrderCustomMappingStrategy<>();
        strategy.setType(clazz);
        return CsvSupport.csvToBeanList(reader, strategy, separator);
    }

    @Override
    public <T> void writeEntries(OutputStream outputStream, List<T> records, Class<T> clazz) throws IOException {
        log.debug("Started writing entries to file");
        // Creating output stream
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, encoding);
        // Create Mapping Strategy
        BeanFieldsOrderCustomMappingStrategy<T> mappingStrategy =
                new BeanFieldsOrderCustomMappingStrategy<>();
        mappingStrategy.setType(clazz);
        CsvSupport.beanListToCsv(writer, mappingStrategy, records, separator, withApplyQuotesToAll);
    }

}
