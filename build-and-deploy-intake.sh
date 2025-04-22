#!/bin/bash
set -e

# Colors for better output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Building and deploying Intake Service...${NC}"

# Step 1: Build the parent project
echo -e "${GREEN}Step 1: Building parent project...${NC}"
mvn clean install -DskipTests

# Step 2: Make sure the Docker containers for dependencies are running
echo -e "${GREEN}Step 2: Ensuring dependencies are running...${NC}"
docker-compose up -d postgres zookeeper kafka

# Step 3: Build a custom Docker image for intake-service
echo -e "${GREEN}Step 3: Building intake-service Docker image...${NC}"
cd dispute-intake-service
docker build -t smartcase/intake-service:latest -f Dockerfile.simple .

# Step 4: Run the intake-service container
echo -e "${GREEN}Step 4: Starting the intake-service container...${NC}"
docker run -d --name intake-service \
  --network smartcase-engine_default \
  -p 8081:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/disputes \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=smartcase \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
  smartcase/intake-service:latest

echo -e "${GREEN}=== Deployment Complete ===${NC}"
echo -e "${YELLOW}Access the intake service at http://localhost:8081/api/disputes${NC}"
echo -e "${YELLOW}To view logs: docker logs -f intake-service${NC}"
echo -e "${YELLOW}To stop: docker stop intake-service${NC}" 