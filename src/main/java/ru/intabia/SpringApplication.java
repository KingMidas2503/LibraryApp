package ru.intabia;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringApplication {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/habrdb", "habrpguser", "pgpwd4habr").cleanDisabled(false).load();
        flyway.clean();
        new SpringApplicationBuilder(SpringApplication.class).run();
    }
}

