package com.demo.portailsaisie.backend.utils.parser.excel;

import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelBindByName;
import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelDate;
import com.demo.portailsaisie.backend.utils.parser.excel.annotation.ExcelNumber;
import com.demo.portailsaisie.backend.utils.parser.RecordProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
/* Class with common excel methods */
public class ExcelSupport {
    private ExcelSupport() {}

    public static Iterable<Row> getRows(final InputStream stream, final ExcelParserContext context)
            throws IOException {

        log.debug("Reading file...");
        Iterable<Row> rows;
        Workbook workbook = WorkbookFactory.create(stream);
        int headerIndex = 0;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (!workbook.isSheetHidden(i)) {
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator =
                        IteratorUtils.filteredIterator(
                                sheet.iterator(),
                                row -> {
                                    // Remove conditional formats in cells
                                    if (row.getRowNum() == headerIndex) {
                                        context.configureHeaders(row);
                                    }
                                    return true;
                                });

                rows = () -> rowIterator;
                log.debug("Workbook parsed.");
                return rows;
            }
        }
        return null;
    }

    public static Row getHeaderRow(final Iterable<Row> rows) {
        return StreamSupport.stream(rows.spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Header row not found"));
    }

    public static Row getHeaderRow(final Iterable<Row> rows, int rowsToSkip) {
        return StreamSupport.stream(rows.spliterator(), false)
                .skip(rowsToSkip) // Skip any uninteresting rows to reach the header row
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Header row not found"));
    }

    public static <O> List<O>  parseContent(
            Iterable<Row> rows,
            Class<O> clazz,
            ExcelParserContext context,
            RecordProcessor processor) {

        log.debug("Starting parsing");
        long time = System.currentTimeMillis();

        try {
            return StreamSupport.stream(rows.spliterator(), false)
                    .skip(1) //skip headers
                    .map(row -> new ExcelRecord(context, row))
                    .flatMap(
                            record ->
                                    // Process Row
                                    processor.processRecord(record, clazz).stream())
                    .collect(Collectors.toList());
        } finally {
            log.debug("Parsing completed in {} ms", System.currentTimeMillis() - time);
        }
    }

    public static <T> void exportToExcel(OutputStream outputStream, List<T> entries, Class<T> clazz, String sheetName) throws IOException {
        log.debug("Starting writing...");
        long time = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        // Populate sheet
        createSheetRows(sheet, entries, clazz, workbook);
        workbook.write(outputStream);
        log.debug("Writing completed in {} ms", System.currentTimeMillis() - time);
        workbook.close();
    }

    public static <T> void createSheetRows(Sheet sheet, List<T> entries, Class<T> clazz, Workbook workbook) {
        createHeaderRow(sheet, clazz);
        for (int i = 0; i < entries.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T entry = entries.get(i);
            populateRow(row, entry, workbook);
        }
    }

    public static <T> void createHeaderRow(Sheet sheet, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // Only fields with ExcelBindByName annotation count
            if (field.isAnnotationPresent(ExcelBindByName.class)) {
                ExcelBindByName fieldAnnotation = field.getAnnotation(ExcelBindByName.class);
                headerRow.createCell(i).setCellValue(fieldAnnotation.column());
            }
        }
    }

    public static <T> void populateRow(Row row, T entry, Workbook workbook) {
        Field[] fields = entry.getClass().getDeclaredFields();
        for (int j = 0; j < fields.length; j++) {
            Field field = fields[j];
            field.setAccessible(true);
            try {
                Object value = field.get(entry);
                if (value != null) {
                    CellStyle cellStyle = createCellStyle(workbook, field);
                    createRowCell(row, value, j, cellStyle);
                }
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            } finally {
                field.setAccessible(false);
            }
        }
    }

    private static CellStyle createCellStyle(Workbook workbook, Field field) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        if (field.isAnnotationPresent(ExcelNumber.class)) {
            String numberFormat = field.getAnnotation(ExcelNumber.class).value();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(numberFormat));
        }
        if (field.isAnnotationPresent(ExcelDate.class)) {
            String dateFormat = field.getAnnotation(ExcelDate.class).value();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(dateFormat));
        }
        return cellStyle;
    }

    public static void createRowCell(Row row, Object value, int index, CellStyle cellStyle) {
        Cell cell = row.createCell(index);
        try {
            double numericValue = Double.parseDouble(value.toString());
            cell.setCellValue(numericValue);
        } catch (NumberFormatException e) {
            if (value instanceof Date date) {
                cell.setCellValue(date);
            } else {
                cell.setCellValue(value.toString());
            }
        }
        cell.setCellStyle(cellStyle);
    }
}
