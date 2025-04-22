#!/bin/bash
# Start Services Script for SmartCase Engine
# Builds and starts all services using Maven and Docker Compose

# Set the current directory to the project root
cd "$(dirname "$0")"

echo "Building all services with Maven..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Maven build failed! Please fix the issues and try again."
    exit 1
fi

echo "Starting all services with Docker Compose..."
docker-compose up -d

echo "Waiting for services to start..."
sleep 5

echo "Checking service status..."
docker-compose ps

echo "All services started successfully!"
echo "You can access the Agent UI dashboard at: http://localhost:8080/dashboard"
echo "You can submit disputes at: http://localhost:8081/api/disputes" 