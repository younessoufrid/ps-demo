package com.demo.portailsaisie.backend.batch.export;

import org.springframework.batch.item.ItemProcessor;

import java.io.File;

public class BatchExportITemProcessor implements ItemProcessor<File, File> {

    @Override
    public File process(File file) throws Exception {
        return new File("path to file");
    }
}
