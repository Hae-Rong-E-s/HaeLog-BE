package com.example.haelogproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HaelogprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaelogprojectApplication.class, args);
    }

}
