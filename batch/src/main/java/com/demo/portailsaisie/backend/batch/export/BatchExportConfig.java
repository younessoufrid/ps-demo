package com.demo.portailsaisie.backend.batch.export;

import com.demo.portailsaisie.backend.batch.listeners.BatchReferentielJobListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
public class BatchExportConfig {
    @Autowired
    private JobBuilder jobBuilder;

    @Autowired
    private StepBuilder stepBuilder;

    @Autowired
    private BatchReferentielJobListener batchReferentielJobListener;

    @Autowired
    private JobLauncher jobLauncher;


    @Bean
    public BatchExportITemReader itemReader() {
        return new BatchExportITemReader();
    }

    @Bean
    public BatchExportITemWriter itemWriter() {
        return new BatchExportITemWriter();
    }

    @Bean
    public BatchExportITemProcessor itemProcessor() {
        return new BatchExportITemProcessor();
    }

}
