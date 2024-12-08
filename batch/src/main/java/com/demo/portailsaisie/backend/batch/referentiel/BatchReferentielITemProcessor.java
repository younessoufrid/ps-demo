package com.demo.portailsaisie.backend.batch.referentiel;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.File;

public class BatchReferentielITemProcessor implements ItemProcessor<File, File> {

    @Override
    public File process(File file) throws Exception {
        return new File("path to file");
    }
}
