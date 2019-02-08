package com.kujakunote.apache.httpclient.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
public class ApacheHttpclientClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApacheHttpclientClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(
            final RestOperations restOperations,
            final ApplicationContext context) {
        return args -> {
            System.out.println(restOperations.getForObject("https://localhost:8443", String.class));

            System.exit(SpringApplication.exit(context, () -> 0));
        };
    }
}
