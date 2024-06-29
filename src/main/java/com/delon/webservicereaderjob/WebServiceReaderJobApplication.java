package com.delon.webservicereaderjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebServiceReaderJobApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(WebServiceReaderJobApplication.class, args);
        context.close();
    }
}
