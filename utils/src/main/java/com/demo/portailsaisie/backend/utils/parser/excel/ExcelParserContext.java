package com.demo.portailsaisie.backend.utils.parser.excel;

import com.demo.portailsaisie.backend.utils.parser.ParserContext;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class ExcelParserContext implements ParserContext {
    private final Map<String, Integer> headerIndexMap;
    private final DataFormatter formatter;

    public ExcelParserContext() {
        super();
        headerIndexMap = new HashMap<>();
        formatter = new DataFormatter();
    }

    int getColumnIndex(String header) {
        if (!this.headerIndexMap.containsKey(header)) {
            log.trace("Unable to locate header '{}' in context", header);
            return -1;
        }
        return this.headerIndexMap.get(header);
    }

    public void configureHeaders(Row headerRow) {
        Iterator<Cell> cellIterator = headerRow.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            addHeaderColumn(cell.getStringCellValue().trim(), cell.getColumnIndex());
        }
    }

    private void addHeaderColumn(final String header, final int columnIndex) {
        this.headerIndexMap.put(header, columnIndex);
    }
    DataFormatter getDataFormatter() {
        return formatter;
    }

}
