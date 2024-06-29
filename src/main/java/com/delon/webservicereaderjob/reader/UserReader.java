package com.delon.webservicereaderjob.reader;

import com.delon.webservicereaderjob.domain.User;
import com.delon.webservicereaderjob.domain.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserReader implements ItemReader<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserReader.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private List<User> users = new ArrayList<>();
    private int page = 1;
    private int userIndex = 0;
    private int total = 0;

    @Value("${spring.batch.chunkSize}")
    private int chunkSize;

    @Value("${spring.batch.pageSize}")
    private int pageSize;

    @Value("${spring.batch.limit}")
    private int limit;

    @Override
    public User read() {
        User user;
        if (userIndex < users.size()) {
            user = users.get(userIndex);
        } else {
            user = null;
        }
        userIndex++;
        return user;
    }

    private List<User> fetchUserDataFromWebService() {
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(String.format("https://gorest.co.in/public/v1/users?page=%d", page),
                                                                          UserResponse.class);
        return Objects.requireNonNull(response.getBody()).data();
    }

    @BeforeChunk
    private void beforeChunk() {
        for (int i = 0; i < chunkSize; i += pageSize) {
            if (total >= limit) return;
            users.addAll(fetchUserDataFromWebService());
            total += pageSize;
            page++;
        }
    }

    @AfterChunk
    private void afterChunk() {
        logger.info("Fim do chunk");
        logger.info("Total users: {}", total);
        userIndex = 0;
        users = new ArrayList<>();
    }
}
