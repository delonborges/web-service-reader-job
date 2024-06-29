package com.delon.webservicereaderjob.step;

import com.delon.webservicereaderjob.domain.User;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class WebServiceReaderStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Value("${spring.batch.chunkSize}")
    private int chunkSize;

    public WebServiceReaderStepConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step webServiceReaderStep(ItemReader<User> reader, ItemWriter<User> writer) {
        return new StepBuilder("webServiceReaderStep", jobRepository).<User, User>chunk(chunkSize, transactionManager).reader(reader).writer(writer).build();
    }
}