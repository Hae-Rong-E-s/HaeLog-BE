package com.example.haelogproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJpaAuditing
@SpringBootApplication
public class HaelogprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaelogprojectApplication.class, args);
    }

}
