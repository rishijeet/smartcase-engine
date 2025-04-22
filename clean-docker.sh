#!/bin/bash
# Clean Docker script for SmartCase Engine
# Stops and removes all Docker containers, networks, and volumes related to the project

echo "Stopping all Docker containers..."
docker-compose down

echo "Removing stopped containers..."
docker rm $(docker ps -a -q) 2>/dev/null || true

echo "Pruning Docker networks..."
docker network prune -f

echo "Pruning Docker volumes..."
docker volume prune -f

echo "Pruning Docker system..."
docker system prune -f

echo "Docker environment cleaned successfully!" 