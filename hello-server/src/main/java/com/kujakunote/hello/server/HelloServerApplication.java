package com.kujakunote.hello.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloServerApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}

