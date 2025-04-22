# Classification Service

## Overview
The Classification Service is responsible for analyzing dispute data and determining appropriate categorization, priority, and routing within the SmartCase Engine system. It uses machine learning models to automate the classification of disputes based on their content and characteristics.

## Author
**Rishijeet**

## Architecture
This service is built with Spring Boot and integrates with machine learning components. It consumes dispute data, applies classification algorithms, and communicates results to dependent services. The service is designed for high throughput and accurate classification.

## Key Features
1. **Dispute Categorization**: Classifies disputes by type (fraud, billing, product, etc.)
2. **Priority Determination**: Assigns priority levels based on dispute attributes and urgency
3. **Resolution Path Prediction**: Suggests optimal processing paths for dispute resolution
4. **Confidence Scoring**: Provides confidence levels for classification decisions
5. **Model Versioning**: Supports multiple classification models with versioning
6. **Feedback Loop**: Incorporates resolution outcomes to improve classification accuracy

## Key Components
1. **ClassificationServiceApplication.java**: Main Spring Boot application entry point
2. **ClassificationController.java**: REST controller for classification API endpoints
3. **ClassificationService.java**: Core service that orchestrates the classification process
4. **ClassificationModel.java**: Interface for different machine learning models
5. **CategoryClassifier.java**: Specialized classifier for categorization
6. **PriorityClassifier.java**: Specialized classifier for priority assignment
7. **ModelTrainingService.java**: Handles model training and updates

## Endpoints
The service exposes the following key endpoints:
- **API**:
  - `POST /classify`: Classify a single dispute
  - `POST /classify/batch`: Process multiple disputes in batch
  - `GET /models`: List available classification models
  - `POST /models/train`: Trigger model training
  - `GET /health`: Service health check

## Local Development
For local development:
1. Build and run using:
   ```
   mvn clean package -DskipTests
   java -jar target/classification-service-1.0.0.jar
   ```
2. The service runs on port 8083 by default
3. Configure application properties to point to local instances of dependencies

## Docker Deployment
The service can be deployed using Docker:
1. Run `docker-compose up -d classification-service` to start the service
2. The service will be available at `http://localhost:8083`
3. Dependencies (Kafka, PostgreSQL) will be automatically started if needed

## Integration
This service integrates with:
- **Intake Service**: Receives disputes for classification
- **Workflow Service**: Provides classification results to guide workflow decisions
- **Kafka**: For event-driven classification processing
- **PostgreSQL**: For storing classification models and results
- Uses the common-module for shared data models and utilities 