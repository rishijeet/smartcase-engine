package com.smartcase.dispute.agent;

/**
 * Main application class for the Agent UI Service
 * 
 * @author Rishijeet
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
@ComponentScan(basePackages = {
    "com.smartcase.dispute.agent"
})
public class AgentUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentUiApplication.class, args);
    }
}
