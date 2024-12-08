package com.demo.portailsaisie.backend.utils.parser;

import com.demo.portailsaisie.backend.utils.parser.csv.CsvParser;
import com.demo.portailsaisie.backend.utils.parser.excel.ExcelParser;

public class FileParserFactory {
    private static ExcelParser.ExcelParserBuilder excelParserBuilder;
    private static CsvParser.CsvParserBuilder csvParserBuilder;

    public static synchronized ExcelParser.ExcelParserBuilder getExcelParserBuilder() {
        if (excelParserBuilder == null) {
            excelParserBuilder = ExcelParser.builder();
        }
        return excelParserBuilder;
    }

    public static synchronized CsvParser.CsvParserBuilder getCsvParserBuilder() {
        if (csvParserBuilder == null) {
            csvParserBuilder = CsvParser.builder();
        }
        return csvParserBuilder;
    }

}
