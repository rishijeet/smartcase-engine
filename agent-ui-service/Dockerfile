# Dockerfile for agent-ui-service
# @author Rishijeet

# Use a single stage to build and run
FROM eclipse-temurin:17-jre

# Set up the working directory
WORKDIR /app

# Copy the pre-built JAR file
COPY target/agent-ui-service-*.jar app.jar

# Set Spring Boot to exclude database configurations
ENV SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
