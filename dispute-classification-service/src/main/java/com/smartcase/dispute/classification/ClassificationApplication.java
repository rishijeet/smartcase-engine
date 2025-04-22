package com.smartcase.dispute.classification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ClassificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassificationApplication.class, args);
    }
}