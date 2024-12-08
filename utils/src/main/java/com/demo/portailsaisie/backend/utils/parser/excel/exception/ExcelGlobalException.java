package com.demo.portailsaisie.backend.utils.parser.excel.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Getter
@Setter
public class ExcelGlobalException extends RuntimeException {

    private static final ReloadableResourceBundleMessageSource messageSource;

    static {
        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("classpath:messages/process");
        messageSource.setUseCodeAsDefaultMessage(true);
    }

    private long lineNumber = -1L;

    public ExcelGlobalException() {
    }

    public ExcelGlobalException(String message) {
        super(message);
    }

    public ExcelGlobalException(String message, long lineNumber, String... args) {
        super(getMessage(message, LocaleContextHolder.getLocale(), args));
        this.lineNumber = lineNumber;
    }

    public static String getMessage(String code, Locale locale, String... args) {
        return messageSource.getMessage(code, args, locale);
    }

}
