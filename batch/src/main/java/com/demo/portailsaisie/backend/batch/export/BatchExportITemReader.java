package com.demo.portailsaisie.backend.batch.export;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.File;

public class BatchExportITemReader implements  ItemReader<File> {

    @Override
    public File read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
}
