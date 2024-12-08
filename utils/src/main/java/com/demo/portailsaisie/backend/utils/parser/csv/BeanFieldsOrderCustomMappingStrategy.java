package com.demo.portailsaisie.backend.utils.parser.csv;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.comparator.LiteralComparator;
import com.opencsv.exceptions.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class BeanFieldsOrderCustomMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        // overriding this method to allow us to preserve the header column name casing
        String[] header = super.generateHeader(bean);
        final int numColumns = headerIndex.findMaxIndex() + 1;
        if (numColumns == -1) {
            return header;
        }

        header = new String[numColumns + 1];

        BeanField beanField;
        for (int i = 0; i <= numColumns; i++) {
            beanField = findField(i);
            String columnHeaderName = extractHeaderName(beanField);
            header[i] = columnHeaderName;
        }
        return header;
    }

    @Override
    public T populateNewBean(String[] line) throws CsvBeanIntrospectionException,
            CsvRequiredFieldEmptyException, CsvDataTypeMismatchException,
            CsvConstraintViolationException, CsvValidationException {

        int headerLength = headerIndex.findMaxIndex() + 1;
        // if the line is shorter than the header fill with null values,
        // else if line is longer than the header don't count the overflowed values
        line = Arrays.copyOf(line, headerLength);
        return super.populateNewBean(line);
    }

    @Override
    protected void loadFieldMap() throws CsvBadConverterException {
        // overriding this method to support setting column order by the order of the object's fields
        if (writeOrder == null) {
            setColumnOrderOnWrite(
                    new LiteralComparator<>(Arrays.stream(type.getDeclaredFields())
                            .map(field -> {
                                if (field.getAnnotation(CsvCustomBindByName.class) != null) {
                                    return field.getAnnotation(CsvCustomBindByName.class).column();
                                }
                                return field.getAnnotation(CsvBindByName.class).column();
                            })
                            .map(String::toUpperCase).toArray(String[]::new)));
        }
        super.loadFieldMap();
    }

    private String extractHeaderName(final BeanField beanField) {
        if (beanField == null || beanField.getField() == null) {
            return StringUtils.EMPTY;
        }

        if (beanField.getField().isAnnotationPresent(CsvBindByName.class)) {
            return beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0].column();
        } else if (beanField.getField().isAnnotationPresent(CsvCustomBindByName.class)) {
            return beanField.getField().getDeclaredAnnotationsByType(CsvCustomBindByName.class)[0].column();
        }
        return StringUtils.EMPTY;
    }
}
