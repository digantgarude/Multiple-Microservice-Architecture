# Multiple Microservice Architecture

A microservices-based event booking system that handles event inventory, bookings, and order processing.

![Multiple Microservices Architecture](/multiple-microservices-arch.png)

## Technology Stack

- Spring Boot
- Spring Cloud Gateway
- Spring Security with OAuth2
- Kafka for async communication
- MySQL for data persistence
- Keycloak for authentication
- Docker to setup services using docker-compose
- OpenAPI (Swagger) for documentation
- Flyway for DB migration.

## Services Overview

### API Gateway Service (Port: 8090)
- Acts as the single entry point for all client requests
- Handles routing to appropriate microservices
- Implements OAuth2 security with Keycloak
- Routes:
  - `/api/v1/inventory/*` -> Inventory Service
  - `/api/v1/booking/*` -> Booking Service
  - `/docs/*` -> Swagger documentation for each service

### Booking Service (Port: 8081)
- Handles customer booking requests
- Validates inventory availability
- Communicates with Inventory Service to check capacity
- Produces booking events to Kafka
- APIs:
  - POST `/api/v1/booking` - Create new booking

### Inventory Service (Port: 8080)
- Manages event and venue information
- Handles capacity management
- APIs:
  - GET `/api/v1/inventory/events` - List all events
  - GET `/api/v1/inventory/event/{eventId}` - Get event details
  - GET `/api/v1/inventory/venue/{venueId}` - Get venue details
  - PUT `/api/v1/inventory/event/{eventId}/capacity/{capacity}` - Update event capacity

### Order Service (Port: 8082)
- Processes confirmed bookings
- Consumes booking events from Kafka
- Updates inventory after order confirmation
- Maintains order records

## Service Communication

```plaintext
Client -> API Gateway -> Microservices

Booking Flow:
1. Client -> API Gateway -> Booking Service
2. Booking Service -> Inventory Service (check availability for capacity)
3. Booking Service -> Kafka (publish booking event)
4. Order Service <- Kafka (consume booking event)
5. Order Service -> Inventory Service (update capacity)
```

[Database Schema](./DB_schema.md)