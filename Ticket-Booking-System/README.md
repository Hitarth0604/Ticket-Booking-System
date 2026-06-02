# Ticket Booking System

A backend-focused ticket booking platform built with **Java Spring Boot** that manages ticket reservations, prevents duplicate bookings during concurrent requests, and generates QR-based digital tickets.

This project focuses on solving real-world problems found in booking platforms such as **seat conflicts, abandoned reservations, transaction consistency, and concurrent user access**.

---

## Why I Built This

Ticket booking systems face a common challenge:

> What happens when multiple users try to book the same seat at exactly the same time?

A basic CRUD application cannot handle this safely. This project was developed to understand how real-world booking systems handle:

- Concurrent booking requests
- Temporary seat reservations
- Expired transactions
- Data consistency
- Reliable backend workflows

The goal was to design a backend system that maintains correctness when multiple users interact with the same resources.

---

# Features

## Ticket Reservation

- View available tickets
- Temporarily lock tickets before confirmation
- Complete ticket bookings
- Cancel existing bookings
- Generate QR-based tickets
- Validate tickets using unique entry tokens

---

## Seat Locking System

When a user selects a ticket, it is not booked immediately.

The ticket follows the lifecycle:

```text
AVAILABLE

    ↓

LOCKED (Temporary Hold)

    ↓

BOOKED
```

A temporary lock prevents other users from booking the same ticket while the current user completes the booking process.

If confirmation is not completed:

```text
Lock expires

      ↓

Background scheduler executes

      ↓

Ticket becomes AVAILABLE again
```

---

# Handling Concurrent Bookings

Problem scenario:

```text
User A selects Ticket #25

User B selects Ticket #25

Both attempt booking simultaneously
```

Without concurrency handling, both requests could reserve the same ticket.

Implemented solution:

- Hibernate Optimistic Locking
- Entity version tracking
- Transaction validation during updates

If another transaction modifies the ticket first, the later request fails safely instead of overwriting existing data.

This prevents race conditions while maintaining application performance.

---

# System Design Decisions


## Why Optimistic Locking?

Optimistic locking was selected because booking conflicts usually happen only during high-demand periods.

Advantages:

- Avoids unnecessary database locks
- Allows better request throughput
- Improves scalability
- Prevents duplicate ticket allocation


---

## Why Scheduled Lock Cleanup?

Users may reserve tickets and abandon the booking process.

Solution:

- Store expiry timestamp for every locked ticket
- Run scheduled background cleanup
- Release expired reservations automatically

This ensures ticket availability remains accurate.

---

# Tech Stack


## Backend

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate ORM
- REST APIs


## Database

- PostgreSQL


## Build & Deployment

- Gradle
- Docker
- Docker Compose


## Core Concepts

- Transaction Management
- Optimistic Locking
- Race Condition Handling
- Scheduled Tasks
- Layered Architecture
- QR Code Generation

---

# Backend Architecture


```text
Client Request

      |

Controller Layer
(API Endpoints)

      |

Service Layer
(Business Logic)

      |

Repository Layer
(Database Access)

      |

PostgreSQL Database
```

---

# API Endpoints


| Method | Endpoint | Description |
|------|-----------|-------------|
| GET | /api/tickets | Fetch all tickets |
| POST | /api/hold | Temporarily reserve ticket |
| POST | /api/ticket | Confirm booking |
| POST | /api/cancel | Cancel ticket |
| GET | /api/qrcode/{entryToken} | Generate QR ticket |
| GET | /api/admit/{entryToken} | Validate ticket entry |

---

# Database Model


## Ticket Entity


| Field | Purpose |
|-|-|
| id | Unique ticket identifier |
| status | Ticket state management |
| lockedBy | User holding reservation |
| lockExpiry | Temporary hold timeout |
| version | Optimistic locking control |
| entryToken | QR validation token |

---

# Local Setup


## Requirements

```text
Java 21
PostgreSQL
Docker
Gradle
```

---

## Start PostgreSQL

```bash
docker run \
-p 5432:5432 \
--name postgres-ticket \
-e POSTGRES_PASSWORD=password \
-d postgres:14
```

---

## Clone Repository


```bash
git clone https://github.com/Hitarth0604/ticket-booking-system.git
```

Move into project:

```bash
cd ticket-booking-system
```

Build:

```bash
./gradlew clean build
```

Run:

```bash
./gradlew bootRun
```

Application:

```text
http://localhost:8080
```

---

# Docker Deployment


Build image:

```bash
docker build -t ticket-booking-system .
```

Run:

```bash
docker-compose up
```

---

# API Testing Screenshots

Recommended structure:

```text
docs/

 ├── available-tickets.png
 ├── lock-ticket-api.png
 ├── booking-success.png
 ├── postgres-record.png
 └── generated-qr.png
```

Example:

```markdown
![Booking API](docs/booking-success.png)
```

---

# Key Learnings

Through this project I explored:

- Designing REST APIs beyond CRUD operations
- Managing database consistency
- Preventing race conditions
- Handling simultaneous user requests
- Implementing transaction-safe workflows
- Structuring Spring Boot applications
- Deploying applications using Docker

---

# Future Improvements

- JWT authentication
- Role based access control
- Redis distributed seat locking
- Payment gateway simulation
- Email ticket delivery
- WebSocket based live seat updates
- Unit testing with JUnit & Mockito

---

# Developer

**Hitarth Parekh**

Backend Developer  
Java | Spring Boot | REST APIs | PostgreSQL


GitHub:  
https://github.com/Hitarth0604


LinkedIn:  
https://www.linkedin.com/in/hitarth-parekh