#!/bin/bash
# Script to stop the agent-ui-service Docker container
# @author Rishijeet

echo "Stopping and removing agent-ui container..."
docker rm -f agent-ui 2>/dev/null || true

echo "Cleanup complete!" 