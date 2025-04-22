#!/bin/bash
set -e

# Colors for better output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Building and deploying SmartCase Dispute Management System...${NC}"

# Step 1: Build the parent project
echo -e "${GREEN}Step 1: Building parent project...${NC}"
mvn clean install -DskipTests

# Step 2: Build the common module
echo -e "${GREEN}Step 2: Building common module...${NC}"
cd dispute-common
mvn clean install -DskipTests
cd ..

# Step 3: Build and deploy with Docker Compose
echo -e "${GREEN}Step 3: Building and deploying with Docker Compose...${NC}"
docker-compose build --no-cache

echo -e "${GREEN}Step 4: Starting the services...${NC}"
docker-compose up -d

# Step 5: Check if all services are running
echo -e "${GREEN}Step 5: Checking service status...${NC}"
echo -e "${YELLOW}Waiting for services to start (30 seconds)...${NC}"
sleep 30
docker-compose ps

echo -e "${GREEN}Step 6: Displaying logs from services...${NC}"
docker-compose logs --tail=10

echo -e "${GREEN}=== Deployment Complete ===${NC}"
echo -e "${YELLOW}Access the application at http://localhost:8080/dashboard${NC}"
echo -e "${YELLOW}Intake Service API: http://localhost:8081/api/disputes${NC}"
echo -e "${YELLOW}Classification Service: http://localhost:8082${NC}"
echo -e "${YELLOW}Workflow Service: http://localhost:8083${NC}"
echo -e "${YELLOW}To view logs: docker-compose logs -f${NC}"
echo -e "${YELLOW}To stop: docker-compose down${NC}" 