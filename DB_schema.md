# Database Schema

## Database: ticketing

### Tables and Columns

#### venue
| Column         | Type         | Constraints                |
|---------------|--------------|---------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT|
| name          | VARCHAR(255) | NOT NULL                  |
| address       | VARCHAR(255) | NOT NULL                  |
| total_capacity| BIGINT       | NOT NULL                  |

#### event
| Column         | Type         | Constraints                |
|---------------|--------------|---------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT|
| name          | VARCHAR(255) | NOT NULL                  |
| venue_id      | BIGINT       | NOT NULL, FOREIGN KEY     |
| total_capacity| BIGINT       | NOT NULL                  |
| left_capacity | BIGINT       | NOT NULL                  |
| ticket_price  | DECIMAL(10,2)| NOT NULL, DEFAULT 10.00   |

#### customer
| Column  | Type         | Constraints                |
|---------|-------------|---------------------------|
| id      | BIGINT      | PRIMARY KEY, AUTO_INCREMENT|
| name    | VARCHAR(255)| NOT NULL                  |
| email   | VARCHAR(255)| NOT NULL                  |
| address | VARCHAR(255)| NOT NULL                  |

#### order
| Column     | Type         | Constraints                |
|-----------|--------------|---------------------------|
| id        | BIGINT       | PRIMARY KEY, AUTO_INCREMENT|
| total     | DECIMAL(10,2)| NOT NULL                  |
| quantity  | BIGINT       | NOT NULL                  |
| placed_at | TIMESTAMP    | DEFAULT CURRENT_TIMESTAMP |
| customer_id| BIGINT      | FOREIGN KEY              |
| event_id  | BIGINT       | FOREIGN KEY              |

### Relationships

1. **event -> venue**
   - Foreign Key: `event.venue_id` references `venue.id`
   - ON DELETE: CASCADE
   - Cardinality: Many-to-One

2. **order -> customer**
   - Foreign Key: `order.customer_id` references `customer.id`
   - ON DELETE: SET NULL
   - Cardinality: Many-to-One

3. **order -> event**
   - Foreign Key: `order.event_id` references `event.id`
   - ON DELETE: SET NULL
   - Cardinality: Many-to-One