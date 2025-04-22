#!/bin/bash
# Script to deploy the agent-ui-service with Docker
# @author Rishijeet

echo "Cleaning up previous Docker resources..."
docker rm -f agent-ui 2>/dev/null || true

echo "Building JAR file..."
cd agent-ui-service
mvn clean package -DskipTests

echo "Building Docker image..."
docker build -t smartcase/agent-ui:latest .

echo "Running Docker container..."
docker run -d -p 8080:8080 \
  -e "SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration" \
  --name agent-ui \
  smartcase/agent-ui:latest

echo "Waiting for service to start..."
sleep 5

# Check if service is healthy
curl -s http://localhost:8080/dashboard > /dev/null
if [ $? -eq 0 ]; then
  echo "Agent UI is running successfully"
  echo "Access the dashboard at http://localhost:8080/dashboard"
  echo "API endpoints available at http://localhost:8080/api/tasks"
else
  echo "Error: Service may not be running correctly"
  docker logs agent-ui
fi