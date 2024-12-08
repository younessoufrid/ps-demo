package com.demo.portailsaisie.backend.utils.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class ProcessException extends RuntimeException implements ProcessExceptionFormat {

    private static final ReloadableResourceBundleMessageSource messageSource;

    static {
        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("classpath:messages/process");
        messageSource.setUseCodeAsDefaultMessage(true);
    }

    private final ProcessErrorEnum processError;

    private final long timestamp;
    private final String message;
    private final String[] args;

    public ProcessException(ProcessErrorEnum processError, String ...args){
        this.processError = processError;
        this.message = getMessage(processError.getKey(), LocaleContextHolder.getLocale(), args);
        this.timestamp = System.currentTimeMillis();
        this.args = args;
    }

    public ProcessErrorEnum getProcessError() {
        return processError;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public int getHttpStatusCode() {
        return processError.getCode();
    }

    @Override
    public String getKey() {
        return processError.getKey();
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    public static String getMessage(String code, Locale locale, String... args) {
        return messageSource.getMessage(code, args, locale);
    }
}
