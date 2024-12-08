package com.demo.portailsaisie.backend.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.exception.ProcessException;
import org.springframework.context.i18n.LocaleContextHolder;

public class CustomComaSeparatorDoubleConverter extends AbstractBeanField {

    public static final String FORMAT = "#0,0#";

    @Override
    public Object convert(String input) throws CsvDataTypeMismatchException {
        // Check if the value contains the '.' decimal separator
        if (input.contains(".")) {
            throw new CsvDataTypeMismatchException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.INVALID_NUMBER_FORMAT.getKey(),
                            LocaleContextHolder.getLocale(),
                            input,
                            FORMAT
            ));
        }
        // Otherwise, parse the value as a double
        try {
            input = input.replace(",", ".");
            if (input.isBlank()) {
                return null;
            }
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new CsvDataTypeMismatchException(
                ProcessException.getMessage(
                        ProcessErrorEnum.INVALID_NUMBER_FORMAT.getKey(),
                        LocaleContextHolder.getLocale(),
                        input,
                        FORMAT
            ));
        }
    }
}
