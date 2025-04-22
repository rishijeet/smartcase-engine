# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Set up the working directory
WORKDIR /app

# Copy the current module
COPY pom.xml .
COPY src ./src

# Set Spring Boot to exclude database configurations
ENV SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

# Build without tests
RUN mvn package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/agent-ui-service-*.jar app.jar
EXPOSE 8080

# Keep the exclude properties for runtime
ENV SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration

ENTRYPOINT ["java", "-jar", "app.jar"] 