package com.kujakunote.urlconnection.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestOperations;

@SpringBootApplication
public class UrlconnectionClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlconnectionClientApplication.class, args);
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

