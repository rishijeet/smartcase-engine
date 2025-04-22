package com.smartcase.dispute.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartcase.dispute.workflow")
@EntityScan("com.smartcase.dispute.workflow")
public class WorkflowApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }
} 