package com.demo.portailsaisie.backend.batch.referentiel;

import com.demo.portailsaisie.backend.batch.listeners.BatchReferentielJobListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
public class BatchReferentielConfig {


    @Autowired
    private JobBuilder jobBuilder;

    @Autowired
    private StepBuilder stepBuilder;

    @Autowired
    private BatchReferentielJobListener batchReferentielJobListener;


    @Bean
    public BatchReferentielITemReader itemReader() {
        return new BatchReferentielITemReader();
    }

    @Bean
    public BatchReferentielITemWriter itemWriter() {
        return new BatchReferentielITemWriter();
    }

    @Bean
    public BatchReferentielITemProcessor itemProcessor() {
        return new BatchReferentielITemProcessor();
    }

}
