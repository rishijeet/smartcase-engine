package com.smartcase.dispute.intake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class IntakeApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntakeApplication.class, args);
    }
}