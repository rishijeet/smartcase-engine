package com.smartcase.dispute.agent;

/**
 * Configuration class for repository beans
 * 
 * @author Rishijeet
 */
import com.smartcase.dispute.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    
    @Bean
    public TaskRepository taskRepository() {
        return new MockTaskRepository();
    }
} 