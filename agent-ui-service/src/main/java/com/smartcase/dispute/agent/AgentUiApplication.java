package com.smartcase.dispute.agent;

/**
 * Main application class for the Agent UI Service
 * 
 * @author Rishijeet
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.smartcase.dispute.agent",
    "com.smartcase.dispute.model"
})
@EntityScan("com.smartcase.dispute.model")
@EnableJpaRepositories("com.smartcase.dispute.model")
public class AgentUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentUiApplication.class, args);
    }
}
