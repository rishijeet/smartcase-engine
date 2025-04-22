# Dispute Workflow Service

## Overview
The Dispute Workflow Service is responsible for orchestrating the business process for handling dispute cases. It receives classified disputes from Kafka, applies business rules, and routes them through the appropriate resolution paths.

## Author
**Rishijeet**

## Architecture

This service is built using Spring Boot with integration to Red Hat's Drools and jBPM frameworks (part of Business Automation Manager Open Edition - BAMOE). It processes dispute events and creates manual tasks for agent review when necessary.

## Business Process Workflow

The core of this service is the BPMN workflow definition (`dispute-workflow.bpmn`) which:

1. **Defines the Dispute Workflow Process**: Outlines a complete business process for handling disputes, starting from receipt of a classified dispute through to resolution.

2. **Integrates with Kafka**: The process starts with a message event that listens for `dispute.classified` messages from Kafka.

3. **Uses Business Rules**: Executes Drools rules (from the `eligibility-rules.drl` file) to determine dispute eligibility.

4. **Decision Gateway**: Based on the eligibility determined by the rules (AUTO_APPROVE, MANUAL_REVIEW, or REJECT), the workflow branches into different paths:
   - Auto-Resolution: For automatically approved disputes
   - Manual Review: For cases requiring human review (shown in Agent UI)
   - Rejection: For disputes that don't meet eligibility criteria

## Rule Engine Integration

The service uses Drools for business rule execution:

- Rules are defined in the `src/main/resources/rules/eligibility-rules.drl` file
- Rule execution is integrated with the BPMN workflow
- The KIE (Knowledge Is Everything) runtime from Drools powers the rule evaluations

## Key Components

1. **WorkflowService.java**: Processes classified disputes from Kafka and creates manual tasks when needed.
2. **WorkflowConfig.java**: Configures Kafka listeners and message processing.
3. **dispute-workflow.bpmn**: BPMN 2.0 definition of the dispute processing workflow.
4. **eligibility-rules.drl**: Drools rules for determining dispute eligibility.

## Endpoints

This service primarily communicates via Kafka messages, but also exposes REST endpoints for task management.

## Configuration

The service requires the following configurations:
- Kafka broker addresses for message consumption
- PostgreSQL database for storing tasks
- JBoss Drools/jBPM configuration for executing the BPMN workflow

## Getting Started

1. Ensure PostgreSQL and Kafka are running
2. Start the service with:
   ```
   mvn spring-boot:run
   ```
3. The service will listen for classified disputes and process them according to the defined workflow

## Development

When extending the workflow process:
1. Modify the BPMN file using a tool like BPMN.io, Red Hat Business Central, or Camunda Modeler
2. Update the Drools rules in eligibility-rules.drl as needed
3. Add corresponding service implementations in Java code 