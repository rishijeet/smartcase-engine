package com.smartcase.dispute.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.smartcase.dispute.model.TaskRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.smartcase.dispute.workflow"})
@EntityScan({"com.smartcase.dispute.workflow"})
@ComponentScan(basePackages = {"com.smartcase.dispute.workflow"})
public class WorkflowApplication {
    
    @Bean
    @Primary
    public TaskRepository modelTaskRepository(WorkflowTaskRepository workflowTaskRepository) {
        return new TaskRepositoryAdapter(workflowTaskRepository);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }
} 