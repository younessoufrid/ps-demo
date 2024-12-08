package com.demo.portailsaisie.backend.batch.export;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.util.List;

public class BatchExportITemWriter implements ItemWriter<File> {

    @Override
    public void write(Chunk<? extends File> chunk) throws Exception {

    }
}
