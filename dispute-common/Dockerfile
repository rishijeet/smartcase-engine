# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy parent POM first
COPY ../pom.xml /app/parent-pom.xml

# Copy the module's POM and set parent path correctly
COPY pom.xml .
RUN sed -i 's|<relativePath>.*</relativePath>|<relativePath>parent-pom.xml</relativePath>|g' pom.xml

# Copy source code
COPY src ./src

# Build the project
RUN mvn clean install -DskipTests

# Stage 2: Create a smaller image with just the JAR
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/dispute-common-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 