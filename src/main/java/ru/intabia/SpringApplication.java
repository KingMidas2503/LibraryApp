package ru.intabia;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringApplication.class).run();
    }
}

