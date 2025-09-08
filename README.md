# MCP based Multi - Microservice Event Booking System

A microservices-based event booking system that handles event inventory, bookings, and order processing.
This project allows event booking through both a traditional api approach as well as newer MCP (Model Context Protocol) based approaches.

![Multiple Microservices Architecture](/images/multiple-microservices-arch.png)

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
- Traditional APIs:
  - POST `/api/v1/booking` - Create new booking
- MCP endpoint `/sse`
  Available Tools:
  - `createBooking` - Create new booking

### Inventory Service (Port: 8080)
- Manages event and venue information
- Handles capacity management
- Traditional APIs:
  - GET `/api/v1/inventory/events` - List all events
  - GET `/api/v1/inventory/event/{eventId}` - Get event details
  - GET `/api/v1/inventory/venue/{venueId}` - Get venue details
  - PUT `/api/v1/inventory/event/{eventId}/capacity/{capacity}` - Update event capacity
- MCP endpoint `/sse`
  Available Tools:
  - `inventoryGetAllEvents` - List all events
  - `inventoryByVenueId` - Get venue details
  - `inventoryForEvent` - Get event details


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

[More Info: Database Schema](./DB_schema.md)

## MCP Service Example using Cline VS Code extenstion.

1. Get details for all the events.
![Get all events](/images/mcp-get-all-events.png)

2. Create a booking for a particular event.
![Create Booking](/images/mcp-create-booking.png)