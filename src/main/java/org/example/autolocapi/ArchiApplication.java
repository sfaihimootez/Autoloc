package org.example.autolocapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.example")
@EnableJpaRepositories(basePackages = "org.example.autoloc.repositories")
@EntityScan(basePackages = "org.example.autoloc.entities")
public class ArchiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchiApplication.class, args);
    }

}
