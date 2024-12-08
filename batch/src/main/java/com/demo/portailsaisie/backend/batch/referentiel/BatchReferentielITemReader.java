package com.demo.portailsaisie.backend.batch.referentiel;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.io.File;

public class BatchReferentielITemReader  implements ItemReader<File> {

    @Override
    public File read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
}
