#!/bin/bash
set -e

# Colors for better output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${YELLOW}Testing SmartCase Dispute Management API...${NC}"

# Test 1: Submit a new dispute
echo -e "${GREEN}Test 1: Submitting a new dispute...${NC}"
curl -s -X POST http://localhost:8081/api/disputes \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "cust-001",
    "transactionReference": "TX-12345",
    "amount": 1500.00,
    "reason": "FRAUD"
  }' | jq || echo "Response is not valid JSON"

echo -e "${YELLOW}Waiting for processing (10 seconds)...${NC}"
sleep 10

# Test 2: Submit another dispute
echo -e "${GREEN}Test 2: Submitting another dispute...${NC}"
curl -s -X POST http://localhost:8081/api/disputes \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "cust-002",
    "transactionReference": "TX-67890",
    "amount": 750.00,
    "reason": "BILLING_ERROR"
  }' | jq || echo "Response is not valid JSON"

echo -e "${YELLOW}Waiting for processing (10 seconds)...${NC}"
sleep 10

# Test 3: Check task list from agent API
echo -e "${GREEN}Test 3: Fetching tasks from agent API...${NC}"
curl -s http://localhost:8080/api/tasks | jq || echo "Response is not valid JSON"

echo -e "${GREEN}Testing complete!${NC}"
echo -e "${YELLOW}You can now visit http://localhost:8080/dashboard to view and manage disputes in the UI.${NC}" 