# SmartCase Dispute Management System

A modular, event-driven dispute management application designed for processing and handling customer dispute cases with a clean, microservices-based architecture.

## Author

**Rishijeet** - Lead Developer

## Architecture

The system includes the following components:

1. **Dispute Intake Service**: REST API for submitting disputes, publishes to Kafka
2. **Dispute Classification Service**: Categorizes disputes based on rules, processes Kafka events
3. **Dispute Workflow Service**: Orchestrates dispute workflows, handles manual tasks
4. **Agent UI Service**: Dashboard for agents to manage and process dispute cases
5. **Common Module**: Shared DTOs and utilities used across services

## Technology Stack

- **Framework**: Spring Boot 3.1.0
- **Messaging**: Apache Kafka
- **Database**: PostgreSQL 13
- **UI**: Thymeleaf, Bootstrap 5
- **Container**: Docker, Docker Compose
- **JDK**: Java 17

## Building and Running

### Prerequisites

- JDK 17+
- Maven 3.6+
- Docker and Docker Compose

### Quick Start

1. Clone the repository:
```bash
git clone https://github.com/rishijeet/smartcase-engine.git
cd smartcase-engine
```

2. Build and deploy the application:
```bash
./build-and-deploy.sh
```

3. Test the API (after the services are up):
```bash
./test-api.sh
```

4. Access the UI dashboard:
```
http://localhost:8080/dashboard
```

### Running Agent UI Service (standalone with mock data)

For local development without database:

1. Deploy with the provided script:
```bash
./deploy-agent-ui.sh
```

2. Access the UI dashboard:
```
http://localhost:8080/dashboard
```

3. Stop the service when done:
```bash
./stop-agent-ui.sh
```

### Manual Steps

1. Build the parent project:
```bash
mvn clean install -DskipTests
```

2. Build the common module:
```bash
cd dispute-common
mvn clean install -DskipTests
cd ..
```

3. Start the services:
```bash
docker-compose up -d
```

4. Check service status:
```bash
docker-compose ps
```

## Service Endpoints

- **Agent UI**: http://localhost:8080/dashboard
- **Intake API**: http://localhost:8081/api/disputes
- **Classification Service**: http://localhost:8082
- **Workflow Service**: http://localhost:8083

## Development

### Module Structure

- `dispute-common`: Shared DTOs and utilities
- `dispute-intake-service`: REST API for dispute submission
- `dispute-classification-service`: Classification logic
- `dispute-workflow-service`: Orchestration and task management
- `agent-ui-service`: Agent dashboard and task management

### Adding a New Service

1. Create a new directory for your service
2. Copy the pom.xml structure from an existing service
3. Update dependencies as needed
4. Add your service to the parent pom.xml `<modules>` section
5. Add the service to docker-compose.yml

## Testing

1. Submit a test dispute:
```bash
curl -X POST http://localhost:8081/api/disputes \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "cust-001",
    "transactionReference": "TX-12345",
    "amount": 1500.00,
    "reason": "FRAUD"
  }'
```

2. View tasks in the UI:
```
http://localhost:8080/dashboard
```

## Stopping

Stop all services:
```bash
docker-compose down
```

Stop and remove volumes:
```bash
docker-compose down -v
```

## Monitoring

Check logs for all services:
```bash
docker-compose logs -f
```

Check logs for a specific service:
```bash
docker-compose logs -f agent-ui
``` 