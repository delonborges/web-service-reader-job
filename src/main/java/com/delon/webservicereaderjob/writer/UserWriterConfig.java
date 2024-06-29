package com.delon.webservicereaderjob.writer;

import com.delon.webservicereaderjob.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserWriterConfig {

    private static final Logger logger = LoggerFactory.getLogger(UserWriterConfig.class);

    @Bean
    public ItemWriter<User> write() {
        return items -> items.forEach(item -> logger.info(item.toString()));
    }
}
