# Intake Service

## Overview
The Intake Service is the entry point for dispute processing in the SmartCase Engine system. It receives dispute data from various sources, validates and normalizes the information, and initiates the dispute resolution workflow by passing the data to downstream services.

## Author
**Rishijeet**

## Architecture
This service is built using Spring Boot and provides both REST APIs and message queue consumers for receiving dispute data. It processes and enriches incoming disputes before sending them for classification and further processing.

## Key Features
1. **Dispute Intake**: Receives dispute data from multiple channels and formats
2. **Data Validation**: Ensures all required fields are present and properly formatted
3. **Data Normalization**: Converts incoming data to a standard format
4. **Duplicate Detection**: Identifies and handles potential duplicate dispute submissions
5. **Initial Enrichment**: Adds metadata and initial processing information
6. **Workflow Initiation**: Submits validated disputes to the workflow service

## Key Components
1. **IntakeServiceApplication.java**: Main Spring Boot application entry point
2. **IntakeController.java**: REST controller for intake API endpoints
3. **KafkaConsumer.java**: Listens for disputes submitted via Kafka
4. **DisputeValidator.java**: Validates dispute data completeness and correctness
5. **DisputeEnricher.java**: Enriches disputes with additional information
6. **WorkflowClient.java**: Communicates with the workflow service

## Endpoints
The service exposes the following key endpoints:
- **API**:
  - `POST /disputes`: Submit a new dispute
  - `GET /disputes/{id}`: Get status of a submitted dispute
  - `POST /disputes/batch`: Submit multiple disputes in a batch
  - `GET /health`: Service health check

## Local Development
For local development:
1. Build and run using:
   ```
   mvn clean package -DskipTests
   java -jar target/intake-service-1.0.0.jar
   ```
2. The service runs on port 8081 by default
3. Configure application properties to point to local instances of dependencies

## Docker Deployment
The service can be deployed using Docker:
1. Run `docker-compose up -d intake-service` to start the service
2. The service will be available at `http://localhost:8081`
3. Dependencies (Kafka, PostgreSQL) will be automatically started if needed

## Integration
This service integrates with:
- **Classification Service**: Sends disputes for classification
- **Workflow Service**: Submits disputes to initiate the workflow
- **Kafka**: For asynchronous dispute processing
- **PostgreSQL**: For storing intake records and status information
- Uses the common-module for shared data models and utilities 