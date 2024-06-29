package com.delon.webservicereaderjob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceReaderJobConfig {

    private final JobRepository jobRepository;

    public WebServiceReaderJobConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job webServiceReaderJob(Step webServiceReaderStep) {
        return new JobBuilder("webServiceReaderJob", jobRepository).start(webServiceReaderStep).incrementer(new RunIdIncrementer()).build();
    }
}
