package com.demo.portailsaisie.backend.utils.parser.excel;

import com.demo.portailsaisie.backend.utils.parser.FileParser;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
@Builder
public class ExcelParser implements FileParser {
    private final ExcelParserContext context = new ExcelParserContext();
    private final ExcelRecordProcessor  processor = new ExcelRecordProcessor();

    @Override
    public <T> List<T> parseEntries(InputStream inputStream, Class<T> clazz) throws IOException {
        log.debug("Started reading rows");
        Iterable<Row> rows = ExcelSupport.getRows(inputStream, context);
        if(rows != null) {
            return ExcelSupport.parseContent(rows, clazz,context, processor);
        }
        return Collections.emptyList();
    }

    @Override
    public <T> void writeEntries(OutputStream outputStream, List<T> records, Class<T> clazz) throws IOException {
        log.debug("Started writing entries to file");
        ExcelSupport.exportToExcel(outputStream, records, clazz, "Data");
    }

}
