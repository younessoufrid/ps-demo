package com.demo.portailsaisie.backend.utils.parser.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.exception.ProcessException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;

public class CustomDateConverter extends AbstractBeanField {

    public static final String FORMAT = "dd/MM/yyyy";

    @Override
    public Object convert(String input) throws CsvConstraintViolationException {
        if (input != null && !input.isBlank()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
            dateFormat.setLenient(false);
            try {
                return dateFormat.parse(input);
            } catch (DateTimeException | ParseException e) {
                throw new CsvConstraintViolationException(CustomDateConverter.class,
                        ProcessException.getMessage(
                                ProcessErrorEnum.INVALID_DATE_FORMAT.getKey(),
                                LocaleContextHolder.getLocale(),
                                input,
                                FORMAT
                        )
                );
            }
        }
        return null;
    }
}
