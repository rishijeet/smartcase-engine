# Dockerfile for agent-ui-service
# @author Rishijeet

# Use a two-stage build
FROM maven:3.8-openjdk-17 AS builder

# Set up the working directory
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build without tests
RUN mvn package -DskipTests

# Second stage: Runtime
FROM eclipse-temurin:17-jre

# Set up the working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/agent-ui-service-*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
