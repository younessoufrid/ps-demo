package com.demo.portailsaisie.backend.utils.parser;

import com.demo.portailsaisie.backend.utils.parser.csv.CsvParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExcelCsvUtils {

    public static <T> List<T> parseExcelFile(InputStream inputStream, Class<T> clazz) throws IOException {
        FileParser fileParser = FileParserFactory.getExcelParserBuilder()
                .build();
        return fileParser.parseEntries(inputStream, clazz);
    }

    public static <T> byte[] generateExcelFile(List<T> records, Class<T> clazz) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileParser fileParser = FileParserFactory.getExcelParserBuilder()
                .build();
        fileParser.writeEntries(outputStream, records, clazz);
        return outputStream.toByteArray();
    }

    public static <T> byte[] generateCsvFile(List<T> records, Class<T> clazz) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CsvParser fileParser = FileParserFactory.getCsvParserBuilder()
                .encoding(StandardCharsets.UTF_8)
                .separator(';')
                .withApplyQuotesToAll(true)
                .build();
        fileParser.writeEntries(outputStream, records, clazz);
        return outputStream.toByteArray();
    }

    public static <T> List<T> parseCsvFile(InputStream inputStream, Class<T> clazz) throws IOException {
        CsvParser fileParser = FileParserFactory.getCsvParserBuilder()
                .encoding(StandardCharsets.UTF_8)
                .separator(';')
                .withApplyQuotesToAll(true)
                .build();
        return fileParser.parseEntries(inputStream, clazz);
    }
}
