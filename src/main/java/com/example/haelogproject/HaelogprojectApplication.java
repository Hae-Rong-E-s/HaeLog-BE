package com.example.haelogproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource("classpath:db.properties")
public class HaelogprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaelogprojectApplication.class, args);
    }

}
