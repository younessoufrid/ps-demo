package com.demo.portailsaisie.backend.utils.parser.excel;

import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.parser.Record;
import com.demo.portailsaisie.backend.utils.parser.excel.exception.ExcelGlobalException;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;

@Slf4j
@Getter
public class ExcelRecord implements Record {

    private final ExcelParserContext context;
    private final Row row;

    ExcelRecord(final ExcelParserContext context, final Row row) {
        this.context = context;
        this.row = row;
    }

    @Override
    public String getString(String field, boolean isRequired) throws IllegalArgumentException,
            ExcelGlobalException {
        int colIndex = context.getColumnIndex(field);
        if (colIndex < 0) {
            if (!isRequired) {
                // This means that the header was not found in the context. Ignore it.
                return null;
            } else {
                throw new IllegalArgumentException("Header " + field + " was not found");
            }
        }
        Cell cell = row.getCell(colIndex);
        String formattedValue = context.getDataFormatter().formatCellValue(cell).trim();

        if (isRequired && StringUtils.isBlank(formattedValue)) {
            throw new ExcelGlobalException(
                    ProcessErrorEnum.REQUIRED_FIELD.getKey(),
                    row.getRowNum(),
                    field
            );
        }
        return formattedValue;
    }

    @Override
    public Integer getInt(String field, boolean isRequired) {
        Long longValue = getLong(field, isRequired);
        return longValue != null ? Math.toIntExact(Math.round(longValue)) : null;
    }

    @Override
    public Long getLong(String field, boolean isRequired) {
        Double doubleValue = getDouble(field, isRequired);
        if (doubleValue != null) {
            Long longValue = Math.round(doubleValue);
            double longAsDouble = (double) longValue;
            if (doubleValue != longAsDouble) {
                throw new IllegalStateException("Error when attempting to read a Long value. Cell. Type Incompatible. " +
                        "Column Header: " + field + " value : " + doubleValue);
            }
            return longValue;
        }
        return null;
    }

    @Override
    public Float getFloat(String field, boolean isRequired) {
        Double doubleValue = getDouble(field, isRequired);
        if (doubleValue != null) {
            if (doubleValue >= Float.MIN_VALUE && doubleValue <= Float.MAX_VALUE) {
                Float f = (float) ((double) doubleValue);
            } else {
                throw new IllegalStateException("Error when attempting to read a Float value. Cell. Type Incompatible. " +
                        "Column Header: " + field + " value : " + doubleValue);
            }
        }
        return null;
    }

    @Override
    public Double getDouble(String field, boolean isRequired) {
        int colIndex = context.getColumnIndex(field);
        if (colIndex < 0) {
            if (!isRequired) {
                // This means that the header was not found in the context. Ignore it.
                return null;
            } else {
                throw new IllegalArgumentException("Header " + field + " was not found");
            }
        }
        Cell cell = row.getCell(colIndex);
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell == null || cell.getCellType() == CellType.BLANK) {
            if (isRequired) {
                throw new ExcelGlobalException(
                        ProcessErrorEnum.REQUIRED_FIELD.getKey(),
                        row.getRowNum(),
                        field
                );
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("Error when attempting to read a Double value. Cell. Type Incompatible. " +
                    "Column Header: " + field);
        }
    }

    @Override
    public Date getDate(String field, boolean isRequired) {
        int colIndex = context.getColumnIndex(field);
        if (colIndex < 0) {
            if (!isRequired) {
                // This means that the header was not found in the context. Ignore it.
                return null;
            } else {
                throw new IllegalArgumentException("Header " + field + " was not found");
            }
        }
        Cell cell = row.getCell(colIndex);
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return cell.getDateCellValue();
        } else if (cell == null || cell.getCellType() == CellType.BLANK) {
            if (isRequired) {
                throw new ExcelGlobalException(
                        ProcessErrorEnum.REQUIRED_FIELD.getKey(),
                        row.getRowNum(),
                        field
                );
            } else {
                return null;
            }
        } else {
            throw new IllegalStateException("Error when attempting to read a Date value. Cell. Type Incompatible. " +
                    "Column Header: " + field);
        }
    }

}
