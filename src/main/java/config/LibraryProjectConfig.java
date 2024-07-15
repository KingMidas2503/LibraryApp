package config;

import Service.Library;
import Service.Reader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
public class LibraryProjectConfig {

    @Bean
    @Scope("prototype")
    public Library library(String title) {
        return new Library(title);
    }
    @Bean
    @Scope("prototype")
    public Reader reader(String name) {
        return new Reader(name);
    }

}
