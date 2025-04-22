# Agent UI Service

## Overview
The Agent UI Service provides a web interface for dispute agents to review, manage, and handle manual tasks related to dispute cases. It serves as the frontend application for human interaction with the SmartCase Dispute Management System.

## Author
**Rishijeet**

## Architecture

This service is built using Spring Boot with Thymeleaf for server-side rendering of HTML pages. It retrieves task data and provides a dashboard for agents to monitor and manage dispute cases.

## Key Features

1. **Dashboard View**: Provides a summary of all tasks categorized by status (pending, approved, rejected) and priority.
2. **Task Management**: Allows agents to view task details, approve, or reject dispute cases.
3. **REST API**: Exposes endpoints for retrieving and managing tasks.

## Key Components

1. **AgentUiApplication.java**: Main Spring Boot application entry point.
2. **DashboardController.java**: Controller for the agent dashboard web interface.
3. **AgentController.java**: REST controller that provides task-related API endpoints.
4. **MockTaskRepository.java**: A mock implementation of the TaskRepository for local development.
5. **RepositoryConfig.java**: Configuration class that defines the TaskRepository bean for use in the application.

## User Interface

The UI is built with:
- Thymeleaf templates for server-side rendering
- Bootstrap for responsive layout and styling
- Custom CSS for additional styling

## Endpoints

The service exposes the following key endpoints:
- **Web Interface**:
  - `/dashboard`: Main agent dashboard showing task statistics and lists
- **API**:
  - `/api/tasks`: List all tasks
  - `/api/tasks/{id}`: Get a specific task
  - `/api/tasks/stats`: Get task statistics
  - `/api/tasks/{id}/approve`: Approve a task
  - `/api/tasks/{id}/reject`: Reject a task

## Local Development

For local development without a database connection:
1. The service uses `MockTaskRepository` which provides in-memory task data
2. Configure `application.properties` to disable database auto-configuration
3. Build and run using:
   ```
   mvn clean package -DskipTests
   java -jar target/agent-ui-service-1.0.0.jar
   ```

## Docker Deployment

The service can be deployed using Docker:
1. Run `./deploy-agent-ui.sh` to build and deploy the service
2. Access the dashboard at `http://localhost:8080/dashboard`
3. To stop the service, run `./stop-agent-ui.sh`

## Integration

This service integrates with:
- The dispute-workflow-service to retrieve tasks created from the workflow process
- API endpoints for task management that would update the task status in a production environment 