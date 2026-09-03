# 🎫 Ticket Management System

A production-style RESTful Ticket Management System built with **Java**, **Spring Boot**, **Spring Data JPA**, **Spring Security**, **JWT**, and **PostgreSQL**.

The application provides complete ticket lifecycle management with CRUD operations, pagination, sorting, dynamic filtering using JPA Specifications, request validation, centralized exception handling, user registration, JWT-based authentication, protected REST APIs, and interactive API documentation using Swagger/OpenAPI.

---

## 🚀 Features

### 🎫 Ticket Management

- ✅ Create Ticket
- ✅ Get All Tickets
- ✅ Get Ticket by ID
- ✅ Update Ticket
- ✅ Update Ticket Status
- ✅ Delete Ticket

### 🔍 Data Retrieval

- ✅ Pagination
- ✅ Dynamic Sorting
- ✅ Dynamic Filtering using JPA Specifications

### 🔐 Authentication & Security

- ✅ User Registration
- ✅ BCrypt Password Hashing
- ✅ User Login
- ✅ Spring Security
- ✅ Custom `UserDetailsService`
- ✅ `AuthenticationManager`
- ✅ JWT Token Generation
- ✅ JWT Token Validation
- ✅ JWT Authentication Filter
- ✅ Protected REST APIs
- ⏳ Role-Based Authorization

### 🧩 Application Features

- ✅ DTO Pattern
- ✅ Bean Validation
- ✅ Global Exception Handling
- ✅ Swagger / OpenAPI Documentation
- ✅ Unit Testing with JUnit 5
- ✅ Mockito

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Security |
| JWT | Stateless Authentication |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Build Tool |
| Lombok | Reduce Boilerplate Code |
| Jakarta Validation | Request Validation |
| Swagger / OpenAPI | API Documentation |
| JUnit 5 | Unit Testing |
| Mockito | Mocking & Unit Testing |

---

## 📂 Project Structure

```text
src/main/java
│
├── config
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   └── TicketController.java
│
├── dto
│   ├── LoginRequestDTO.java
│   ├── LoginResponseDTO.java
│   ├── UserRegistrationDTO.java
│   ├── UserRegistrationResponseDTO.java
│   └── ...
│
├── entity
│   ├── AppUser.java
│   └── Ticket.java
│
├── enums
│   ├── Role.java
│   ├── Priority.java
│   └── ...
│
├── exception
│   └── GlobalExceptionHandler.java
│
├── repository
│   ├── AppUserRepository.java
│   └── TicketRepository.java
│
├── security
│   ├── CustomUserDetailsService.java
│   └── JwtAuthenticationFilter.java
│
├── service
│   ├── AuthService.java
│   ├── JwtService.java
│   └── TicketService.java
│
├── specifications
│   └── TicketSpecification.java
│
└── TicketManagementSystemApplication.java
