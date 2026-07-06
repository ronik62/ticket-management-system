# 🎫 Ticket Management System

A production-style RESTful Ticket Management System built with **Java**, **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**. The application provides complete ticket lifecycle management with CRUD operations, pagination, sorting, dynamic filtering using JPA Specifications, validation, exception handling, and interactive API documentation using Swagger/OpenAPI.

---

## 🚀 Features

- ✅ Create Ticket
- ✅ Get All Tickets
- ✅ Get Ticket by ID
- ✅ Update Ticket
- ✅ Update Ticket Status
- ✅ Delete Ticket
- ✅ Pagination
- ✅ Dynamic Sorting
- ✅ Dynamic Filtering using JPA Specifications
- ✅ DTO Pattern
- ✅ Bean Validation
- ✅ Global Exception Handling
- ✅ Swagger / OpenAPI Documentation

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Build Tool |
| Lombok | Reduce Boilerplate Code |
| Jakarta Validation | Request Validation |
| Swagger / OpenAPI | API Documentation |

---

## 📂 Project Structure

```
src/main/java
│
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── service
├── specifications
└── TicketManagementSystemApplication
```

---

## 📌 REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tickets` | Create a new ticket |
| GET | `/api/tickets` | Get all tickets with pagination, sorting and filtering |
| GET | `/api/tickets/{id}` | Get ticket by ID |
| PUT | `/api/tickets/{id}` | Update ticket |
| PUT | `/api/tickets/{id}/status` | Update ticket status |
| DELETE | `/api/tickets/{id}` | Delete ticket |

---

## 🔍 Filtering

Filter tickets dynamically.

Example:

```http
GET /api/tickets?status=OPEN&priority=P1&requesterName=Ronik
```

---

## 📄 Pagination

Retrieve tickets page by page.

Example:

```http
GET /api/tickets?page=0&size=5
```

---

## ↕️ Sorting

Sort tickets by different fields.

Newest tickets:

```http
GET /api/tickets?sortBy=createdAt&direction=desc
```

Sort by priority:

```http
GET /api/tickets?sortBy=priority&direction=asc
```

Supported sort fields:

- createdAt
- priority
- status
- requesterName

---

## 📚 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI JSON

```
http://localhost:8080/v3/api-docs
```

---

## 🧪 Validation

The application validates incoming requests using Jakarta Validation.

Examples:

- Requester name cannot be blank
- Subject cannot be blank
- Description cannot be blank
- Priority is mandatory

---

## ⚠️ Exception Handling

Centralized exception handling using `@RestControllerAdvice`.

Handles:

- Ticket Not Found (404)
- Validation Errors (400)
- Invalid Sort Fields (400)
- Invalid Request Parameters (400)

---

## 🏗️ Architecture

The project follows a layered architecture.

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
PostgreSQL
```

---

## 🧩 Design Patterns & Concepts Used

- Layered Architecture
- DTO Pattern
- Repository Pattern
- Dependency Injection
- JPA Specifications
- Pagination
- Sorting
- Bean Validation
- Global Exception Handling
- RESTful API Design

---

## ▶️ Running the Project

1. Clone the repository

```bash
git clone <repository-url>
```

2. Navigate to the project

```bash
cd ticket-management-system
```

3. Configure PostgreSQL in `application.properties`

4. Run the application

```bash
mvn spring-boot:run
```

---

## 🚧 Upcoming Features

- Unit Testing (JUnit 5)
- Mockito
- Spring Security
- JWT Authentication
- Microsoft Teams Notification for P1 & P2 Tickets
- Scheduled Ticket Escalation
- Docker Support
- Deployment

---

## 👨‍💻 Author

**Ronik Kumbhar**

Java Backend Developer | Spring Boot | REST APIs | PostgreSQL