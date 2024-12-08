package com.demo.portailsaisie.backend.batch.referentiel;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

public class BatchReferentielITemWriter implements ItemWriter<File> {

    @Override
    public void write(Chunk<? extends File> chunk) throws Exception {

    }
}
