# 🎫 Ticket Management System

A production-style **Ticket Management System** built using **Java, Spring Boot, Spring Data JPA, PostgreSQL, and Spring Security with JWT authentication**.

The project is designed to demonstrate how a real-world backend application is structured, secured, tested, and exposed through REST APIs.

---

# 🚀 Features

- ✅ Ticket CRUD operations
- ✅ DTO-based request/response handling
- ✅ Bean Validation
- ✅ Global Exception Handling
- ✅ Pagination
- ✅ Sorting
- ✅ Dynamic Filtering using JPA Specifications
- ✅ PostgreSQL database integration
- ✅ Spring Data JPA / Hibernate
- ✅ Unit Testing with JUnit 5 and Mockito
- ✅ User Registration
- ✅ BCrypt Password Hashing
- ✅ Spring Security
- ✅ JWT Authentication
- ✅ JWT Authentication Filter
- ✅ Protected REST APIs
- ⏳ Role-Based Authorization
- ⏳ Team Management
- ⏳ Notifications
- ⏳ Dockerization
- ⏳ Deployment

---

# 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Web | REST APIs |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| PostgreSQL | Relational Database |
| Spring Security | Authentication & Security |
| JWT | Stateless Authentication |
| BCrypt | Password Hashing |
| JUnit 5 | Unit Testing |
| Mockito | Mocking Dependencies |
| Maven | Dependency Management |
| Swagger / OpenAPI | API Documentation |

---

# 🏗️ Architecture

The application follows a layered architecture.

                    ┌──────────────────────┐
                    │       CLIENT         │
                    │ Postman / Frontend   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      CONTROLLER      │
                    │   REST API Layer     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       SERVICE        │
                    │   Business Logic     │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     REPOSITORY       │
                    │   Spring Data JPA    │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      DATABASE        │
                    │     PostgreSQL       │
                    └──────────────────────┘

### Layer Responsibilities

### Controller

Responsible for:

- Receiving HTTP requests
- Validating request DTOs
- Calling service methods
- Returning HTTP responses

### Service

Responsible for:

- Business logic
- Validation
- Processing application rules
- Calling repositories

### Repository

Responsible for:

- Database interaction
- CRUD operations
- Dynamic queries
- Pagination
- Sorting

### Database

PostgreSQL stores:

- Users
- Tickets
- Ticket-related information

---

# 📁 Project Structure

    src
    └── main
        └── java
            └── com.ronik.ticket_management_system
                │
                ├── controller
                │   ├── AuthController.java
                │   └── TicketController.java
                │
                ├── service
                │   ├── AuthService.java
                │   ├── TicketService.java
                │   ├── JwtService.java
                │   └── CustomUserDetailsService.java
                │
                ├── repository
                │   ├── AppUserRepository.java
                │   └── TicketRepository.java
                │
                ├── entity
                │   ├── AppUser.java
                │   └── Ticket.java
                │
                ├── dto
                │   ├── LoginRequestDTO.java
                │   ├── LoginResponseDTO.java
                │   ├── UserRegistrationDTO.java
                │   ├── UserRegistrationResponseDTO.java
                │   └── TicketDTO.java
                │
                ├── enums
                │   ├── Role.java
                │   ├── Difficulty.java
                │   └── ...
                │
                ├── specification
                │   └── TicketSpecification.java
                │
                ├── exception
                │   ├── GlobalExceptionHandler.java
                │   └── ...
                │
                └── security
                    ├── SecurityConfig.java
                    └── JwtAuthenticationFilter.java

---

# 🔐 Security Architecture

The application uses **Spring Security + JWT** for stateless authentication.

                         LOGIN REQUEST
                              │
                              ▼
                  ┌─────────────────────────┐
                  │     AuthController      │
                  └────────────┬────────────┘
                               │
                               ▼
                  ┌─────────────────────────┐
                  │       AuthService       │
                  └────────────┬────────────┘
                               │
                               ▼
                  ┌─────────────────────────┐
                  │ AuthenticationManager   │
                  └────────────┬────────────┘
                               │
                               ▼
                  ┌─────────────────────────┐
                  │ UserDetailsService      │
                  └────────────┬────────────┘
                               │
                               ▼
                  ┌─────────────────────────┐
                  │      UserDetails        │
                  │ username + password     │
                  │ authorities             │
                  └────────────┬────────────┘
                               │
                               ▼
                  ┌─────────────────────────┐
                  │     PasswordEncoder     │
                  │        BCrypt           │
                  └────────────┬────────────┘
                               │
                         Authentication
                           successful
                               │
                               ▼
                  ┌─────────────────────────┐
                  │       JwtService        │
                  │      Generate JWT       │
                  └────────────┬────────────┘
                               │
                               ▼
                         JWT RESPONSE

---

# 🔑 JWT Request Flow

After login, the client receives a JWT.

For every protected request:

             CLIENT
                │
                │ Authorization:
                │ Bearer <JWT>
                ▼
      ┌───────────────────────┐
      │ JwtAuthenticationFilter│
      └───────────┬───────────┘
                  │
                  ▼
          Extract JWT Token
                  │
                  ▼
          Extract Username
                  │
                  ▼
      CustomUserDetailsService
                  │
                  ▼
          Load UserDetails
                  │
                  ▼
          Validate JWT
          ┌───────┴────────┐
          │                │
       Invalid           Valid
          │                │
          ▼                ▼
       Reject       Create Authentication
                           │
                           ▼
                  SecurityContextHolder
                           │
                           ▼
                   Protected Controller
                           │
                           ▼
                         Response

---

# 🪪 JWT Structure

A JWT consists of three parts:

    HEADER.PAYLOAD.SIGNATURE

### Header

Contains information about the token.

Example:

    {
      "alg": "HS256",
      "typ": "JWT"
    }

### Payload

Contains claims such as:

    {
      "sub": "alexdev",
      "iat": "...",
      "exp": "..."
    }

### Signature

The signature is generated using the application's secret key.

It allows the server to verify that the token was not modified.

---

# 🔐 Authentication vs Authorization

### Authentication

Answers:

> Who are you?

Example:

    Username + Password
            ↓
    Authentication
            ↓
    JWT

### Authorization

Answers:

> What are you allowed to do?

Example:

    USER
     ↓
    Can access normal ticket APIs

    ADMIN
     ↓
    Can access administrative APIs

Role-based authorization is planned as the next security feature.

---

# 👤 User Registration

Users can register using:

    POST /auth/register

Example request:

    {
      "username": "alexdev",
      "email": "alexdev@gmail.com",
      "password": "Alex@12345"
    }

During registration:

    Raw Password
         │
         ▼
    BCrypt PasswordEncoder
         │
         ▼
    Hashed Password
         │
         ▼
    Database

The application never stores the user's raw password.

---

# 🔑 Login

Users can authenticate using:

    POST /auth/login

Example:

    {
      "username": "alexdev",
      "password": "Alex@12345"
    }

Login flow:

    Username + Password
            │
            ▼
    AuthenticationManager
            │
            ▼
    UserDetailsService
            │
            ▼
    Load UserDetails
            │
            ▼
    BCrypt Password Verification
            │
            ▼
    Authentication Successful
            │
            ▼
    JwtService
            │
            ▼
    JWT Token

The client then uses the JWT for subsequent protected requests.

---

# 🛡️ Protected APIs

Protected APIs require the JWT in the Authorization header.

    Authorization: Bearer <JWT>

Example:

    GET /api/tickets

Request:

    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

The `JwtAuthenticationFilter` validates the token before the request reaches the protected controller.

---

# 🎫 Ticket APIs

## Create Ticket

    POST /api/tickets

Creates a new ticket.

---

## Get All Tickets

    GET /api/tickets

Returns tickets using pagination.

---

## Get Ticket By ID

    GET /api/tickets/{id}

Returns a specific ticket.

---

## Update Ticket

    PUT /api/tickets/{id}

Updates an existing ticket.

---

## Delete Ticket

    DELETE /api/tickets/{id}

Deletes a ticket.

---

# 📄 Pagination

Pagination prevents the application from loading every record at once.

Example:

    GET /api/tickets?page=0&size=5

The response contains:

    Page
     ├── content
     ├── pageNumber
     ├── pageSize
     ├── totalElements
     ├── totalPages
     └── ...

### Why Pagination?

Without pagination:

    Database
       ↓
    100,000 records
       ↓
    Application
       ↓
    Memory / Performance problems

With pagination:

    Database
       ↓
    Required page only
       ↓
    Application
       ↓
    Better performance

---

# 🔃 Sorting

Tickets can be sorted using Spring Data's `Sort`.

Example:

    GET /api/tickets?sort=createdAt,desc

Sorting controls the order of records.

Pagination controls how many records are returned.

They can also be combined:

    GET /api/tickets?page=0&size=5&sort=createdAt,desc

---

# 🔎 Dynamic Filtering

The project uses **JPA Specifications** for dynamic filtering.

Instead of creating multiple repository methods such as:

    findByStatus()
    findByPriority()
    findByStatusAndPriority()
    findByStatusAndPriorityAndTitle()
    ...

Specifications allow optional filters to be combined dynamically.

Example:

    GET /api/tickets?status=OPEN&priority=P1

Conceptually:

    Request Parameters
           │
           ▼
    Optional Filters
           │
           ▼
    JPA Specification
           │
           ▼
    Dynamic Query
           │
           ▼
    Database

This makes the filtering logic more flexible and maintainable.

---

# ✅ Validation

The application uses **Jakarta Bean Validation**.

Examples of validations:

    @NotBlank
    @NotNull
    @Email

Example:

    @NotBlank(message = "username is required")
    private String username;

Invalid requests are rejected before reaching the business logic.

---

# 🚨 Global Exception Handling

The application uses a centralized exception handling mechanism.

Instead of handling exceptions inside every controller:

    Controller 1 → exception handling
    Controller 2 → exception handling
    Controller 3 → exception handling

A global handler provides centralized handling:

    Controller
        │
        ▼
    Exception
        │
        ▼
    GlobalExceptionHandler
        │
        ▼
    Consistent HTTP Response

This keeps controllers cleaner and makes API error responses more consistent.

---

# 🧪 Testing

The project uses:

- JUnit 5
- Mockito

Unit tests are used to test service-layer business logic independently from external dependencies.

Example testing approach:

    Service
      │
      ├── Mock Repository
      ├── Mock Dependencies
      │
      ▼
    JUnit + Mockito
      │
      ▼
    Assertions

---

# 🧠 Important Spring Security Components

## UserDetailsService

Responsible for loading user information from the database.

    Username
       ↓
    UserDetailsService
       ↓
    Database
       ↓
    UserDetails

---

## UserDetails

Represents the authenticated user's security information.

It contains information such as:

    Username
    Password
    Authorities / Roles
    Account Status

---

## AuthenticationManager

Responsible for performing authentication.

Conceptually:

    Credentials
         ↓
    AuthenticationManager
         ↓
    UserDetailsService
         ↓
    Password Verification
         ↓
    Authentication

---

## SecurityContextHolder

Stores the current request's authenticated `Authentication`.

    JWT
     ↓
    JwtAuthenticationFilter
     ↓
    Authentication
     ↓
    SecurityContextHolder
     ↓
    Spring Security

This allows Spring Security to know which user is authenticated during the current request.

---

# 🧩 Design Concepts Demonstrated

This project demonstrates several important backend concepts:

- Layered Architecture
- Separation of Concerns
- Dependency Injection
- DTO Pattern
- Repository Pattern
- Service Layer Pattern
- Strategy-style dynamic querying through Specifications
- Stateless Authentication
- JWT Authentication
- Password Hashing
- Authentication vs Authorization
- Pagination
- Sorting
- Dynamic Filtering
- Exception Handling
- Unit Testing
- REST API Design

---

# 📚 API Documentation

Swagger/OpenAPI can be used to document and test the REST APIs.

Once configured, API documentation can be accessed through the application's Swagger UI.

---

# ⚙️ Configuration

The application requires PostgreSQL configuration.

Example configuration:

    spring.datasource.url=jdbc:postgresql://localhost:5432/ticket_management
    spring.datasource.username=your_username
    spring.datasource.password=your_password

JWT configuration:

    jwt.secret=your_secret_key
    jwt.expiration=3600000

The JWT expiration value represents the token lifetime in milliseconds.

---

# ▶️ Running the Project

## 1. Clone the repository

    git clone <repository-url>

## 2. Configure PostgreSQL

Create the required PostgreSQL database.

## 3. Configure application properties

Set:

    Database URL
    Database Username
    Database Password
    JWT Secret
    JWT Expiration

## 4. Build the project

    mvn clean install

## 5. Run the application

    mvn spring-boot:run

The application will start on the configured Spring Boot port.

---

# 🔄 Complete Application Flow

                         CLIENT
                            │
                            ▼
                    ┌───────────────┐
                    │ REST API      │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │   Security    │
                    │ JWT Filter    │
                    └───────┬───────┘
                            │
                       JWT Valid?
                       ┌────┴────┐
                       │         │
                      NO        YES
                       │         │
                       ▼         ▼
                    Reject   Authentication
                                 │
                                 ▼
                        SecurityContextHolder
                                 │
                                 ▼
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
                                 │
                                 ▼
                             Response

---

# 🗺️ Project Roadmap

### Completed

- [x] Ticket CRUD
- [x] DTOs
- [x] Bean Validation
- [x] Global Exception Handling
- [x] Pagination
- [x] Sorting
- [x] Dynamic Filtering
- [x] JPA Specifications
- [x] JUnit 5
- [x] Mockito
- [x] Spring Security
- [x] User Registration
- [x] BCrypt Password Hashing
- [x] JWT Authentication
- [x] JWT Authentication Filter
- [x] Protected APIs

### Upcoming

- [ ] Role-Based Authorization
- [ ] Team Management
- [ ] Ticket Assignment
- [ ] Notifications
- [ ] Docker
- [ ] Deployment
- [ ] Production-level security improvements
- [ ] Final testing and optimization

---

# 🔮 Upcoming Features

## Role-Based Authorization

Introduce different permissions based on user roles.

Example:

    USER
     ├── View Tickets
     └── Create / Update own Tickets

    ADMIN
     ├── Manage Users
     ├── Manage Tickets
     └── Administrative Operations

---

## Team Management

Allow users to be grouped into teams and tickets to be assigned to specific teams.

---

## Notifications

Add notifications for events such as:

    Ticket Created
    Ticket Assigned
    Ticket Updated
    Ticket Status Changed

---

## Docker

Containerize the application and PostgreSQL environment.

---

## Deployment

Deploy the application to a cloud environment and make the APIs publicly accessible.

---

# 🎯 Project Goals

The primary goal of this project is not just to build CRUD APIs, but to understand how a real backend application is designed.

The project focuses on:

    Java
      ↓
    Spring Boot
      ↓
    REST APIs
      ↓
    Database
      ↓
    Security
      ↓
    Testing
      ↓
    Scalable Backend Design

---

# 👨‍💻 Author

**Ronik**

Java Backend Developer

Technologies:

    Java
    Spring Boot
    Spring Security
    JWT
    Spring Data JPA
    PostgreSQL
    REST APIs
    JUnit
    Mockito
