# Journal App

A secure, feature-rich journal application built with Spring Boot that enables users to create, manage, and analyze their journal entries with sentiment tracking, weather integration, and automated insights.

## Overview

Journal App is a RESTful API backend service that provides users with a private journaling platform. The application supports user authentication via JWT tokens and Google OAuth 2.0, offers sentiment analysis on journal entries, integrates real-time weather data, and delivers weekly email summaries to help users understand their emotional patterns over time.

## Features

### User Management
- User registration and authentication with secure password hashing (BCrypt)
- JWT-based stateless authentication with configurable expiration
- Google OAuth 2.0 integration for seamless sign-in
- Role-based access control (USER and ADMIN roles)
- Profile management including username and password updates
- Account deletion capability

### Journal Entry Management
- Full CRUD operations for journal entries
- Automatic timestamp recording for each entry
- Sentiment tagging support (HAPPY, SAD, ANGRY, ANXIOUS)
- User-specific entry isolation ensuring privacy

### Sentiment Analysis
- Opt-in weekly sentiment analysis feature
- Automated analysis of journal entries from the past 7 days
- Email notifications with sentiment summaries sent every Sunday
- Kafka-based asynchronous processing with email fallback

### Weather Integration
- Real-time weather data integration via Weather API
- Redis caching for optimized API performance (5-minute TTL)
- Weather information displayed alongside user greetings

### Administrative Features
- View all registered users
- Create administrator accounts
- Clear application cache on demand

### Scheduled Tasks
- Weekly sentiment analysis and email delivery (Sunday 9 AM)
- Periodic cache clearing (every 20 minutes)

## Technology Stack

### Core Framework
- Spring Boot 2.7.16
- Java 8
- Maven

### Database
- MongoDB (primary data store with transaction support)
- Redis (caching layer)

### Security
- Spring Security
- JWT (JJWT 0.12.5)
- BCrypt password encoding
- Google OAuth 2.0

### Messaging
- Apache Kafka (event streaming)
- Spring Kafka

### External Integrations
- Weather API (weatherapi.com)

### Documentation and Monitoring
- Swagger/OpenAPI 3.0
- Spring Actuator

### Additional Libraries
- Lombok (boilerplate reduction)
- Logback (structured logging with file rotation)

## API Documentation

The application provides Swagger UI for interactive API documentation. Once the application is running, access the documentation at:

```
http://localhost:8080/swagger-ui.html
```

### API Endpoints

#### Public Endpoints (No Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/public/signup` | Register a new user account |
| POST | `/public/login` | Authenticate and receive JWT token |
| GET | `/public/health-check` | Check application health status |

#### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/auth/google/callback` | Google OAuth callback handler |

#### User Endpoints (Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user` | Get user greeting with current weather |
| PUT | `/user` | Update user profile |
| DELETE | `/user` | Delete user account |

#### Journal Endpoints (Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/journal` | Retrieve all journal entries |
| POST | `/journal` | Create a new journal entry |
| GET | `/journal/id/{id}` | Retrieve a specific journal entry |
| PUT | `/journal/id/{id}` | Update a journal entry |
| DELETE | `/journal/id/{id}` | Delete a journal entry |

#### Admin Endpoints (Admin Role Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/all-users` | Retrieve all users |
| POST | `/admin/create-admin-user` | Create an admin user |
| GET | `/admin/clear-app-cache` | Clear application cache |

## Configuration

### Prerequisites

Before running the application, ensure you have the following installed and configured:

- Java 8 or higher
- Maven 3.6+
- MongoDB instance
- Redis instance
- Apache Kafka broker (optional, for sentiment analysis)

### Environment Setup

The application uses Spring profiles for environment-specific configuration:

- `dev` - Development environment
- `prod` - Production environment

### Required Configuration Properties

Configure the following in your `application.yml` or `application-{profile}.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: your-mongodb-uri
      database: journaldb

  redis:
    host: localhost
    port: 6379

  kafka:
    bootstrap-servers: localhost:9092

weather:
  api:
    key: your-weather-api-key

google:
  client:
    id: your-google-client-id
    secret: your-google-client-secret
```

### Security Configuration

The application enforces the following security rules:

- `/public/**` and `/auth/**` - Publicly accessible
- `/journal/**` and `/user/**` - Require authentication
- `/admin/**` - Require ADMIN role
- CSRF protection is disabled (appropriate for stateless REST APIs)
- Session management is stateless

## Running the Application

### Development Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Building the Application

```bash
mvn clean package
java -jar target/journalApp-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## Authentication Flow

1. Register a new account via `/public/signup` with username, email, and password
2. Authenticate via `/public/login` to receive a JWT token
3. Include the JWT token in the `Authorization` header for subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```
4. Tokens expire after 1 hour and require re-authentication

Alternatively, users can authenticate using Google OAuth 2.0 for a streamlined sign-in experience.

## Caching Strategy

The application implements a multi-level caching strategy:

- **Weather Data**: Cached in Redis with a 5-minute TTL to reduce external API calls
- **Application Configuration**: Loaded into memory at startup and refreshed periodically
- **Cache Clearing**: Automated every 20 minutes via scheduled task, with manual clearing available for admins

## Logging

The application uses Logback for structured logging with the following configuration:

- Console output for development
- File-based logging with rotation
- Maximum file size: 10MB
- Log retention: 10 days
- Log format includes timestamp, thread name, log level, logger name, and message

## Monitoring

Spring Actuator provides health checks and monitoring endpoints. Access the health endpoint at:

```
http://localhost:8080/actuator/health
```
