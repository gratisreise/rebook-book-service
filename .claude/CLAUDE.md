# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot microservice for the Rebook platform that manages book-related operations including search, registration, recommendations, reviews, and bookmarks. It's part of a larger microservices architecture with service discovery, external API integrations, and messaging capabilities.

## Development Commands

### Build and Test
```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run tests and generate coverage report
./gradlew test jacocoTestReport

# Clean build
./gradlew clean build

# Run bootJar only (without tests)
./gradlew bootJar

# Run application locally
./gradlew bootRun
```

### Docker Operations
```bash
# Build Docker image
docker build -t nooaahh/rebook-book-service:latest .

# Build for multiple platforms and push
docker buildx build --platform=linux/amd64 -t nooaahh/rebook-book-service --push .

# Run with Docker Compose
docker-compose up -d
```

### Development
```bash
# Run with dev profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Check dependencies
./gradlew dependencies
```

## High-Level Architecture

### Service Architecture
This is a **Spring Boot microservice** with the following key architectural patterns:

- **Service Discovery**: Uses Eureka client for service registration and discovery
- **Configuration Management**: Integrates with Spring Cloud Config Server
- **API Gateway Pattern**: Designed to work behind an API gateway
- **External API Integration**: Feign clients for Naver Books API and User Service
- **Messaging**: RabbitMQ integration for event publishing
- **Caching**: Redis integration for performance optimization

### Core Domain Model
The service manages three main entities:
- **Book**: Core book information with ISBN, title, author, category, etc.
- **BookReview**: User reviews and ratings for books
- **BookMark**: User bookmarking functionality with composite key (userId + bookId)

### Service Integration Points

#### External APIs
- **Naver Books API**: For external book search functionality
- **Gemini AI API**: For automatic book categorization
- **User Service**: Feign client for user-related operations and preferences

#### Messaging
- **RabbitMQ**: Publishes notifications when new books are registered
- **Notification Publisher**: Sends category-based notifications to interested users

#### Data Storage
- **PostgreSQL**: Primary database for book data
- **Redis**: Caching layer for improved performance

### Key Service Layers

#### Controllers (`controller/`)
- **BookController**: Main API endpoints for book operations
- **BookReviewController**: Review management endpoints
- **BookMarkController**: Bookmark management endpoints

#### Services (`service/`)
- **BookService**: Core business logic for book operations
- **BookReviewService**: Review-specific business logic
- **BookMarkService**: Bookmark-specific business logic
- **ApiService**: External API integration (Naver, Gemini)
- **BookReader/BookReviewReader**: Data access abstraction layers

#### External Integration (`feigns/`)
- **NaverClient**: Feign client for Naver Books API
- **UserClient**: Feign client for user service communication

#### Common Infrastructure (`common/`)
- **ResponseService**: Standardized API response formatting
- **Result classes**: Common result wrapper classes (SingleResult, ListResult, etc.)
- **GlobalExceptionHandler**: Centralized exception handling

### Configuration Profiles
- **dev**: Development profile with config server integration
- **prod**: Production profile with optimized settings
- **Default**: Base configuration with active profile set to 'dev'

### Key Dependencies
- **Spring Boot 3.3.13** with Java 17
- **Spring Cloud** for microservices patterns (Config, Eureka, OpenFeign)
- **Spring Data JPA** with PostgreSQL
- **Spring AMQP** for RabbitMQ integration
- **Spring Data Redis** for caching
- **SpringDoc OpenAPI** for API documentation
- **Gemini AI Client** for AI-powered categorization
- **Lombok** for code generation
- **Jacoco** for test coverage reporting

### Testing Strategy
- **JUnit Platform** for test execution
- **Spring Boot Test** for integration testing
- **Spring Rabbit Test** for messaging tests
- **Jacoco** for coverage reporting (HTML reports enabled)

### Monitoring and Observability
- **Spring Actuator** for health checks and metrics
- **Sentry** for error tracking and monitoring
- **Prometheus** metrics collection

## Important Implementation Notes

### Service Communication
- Uses **OpenFeign** for synchronous HTTP calls to other services
- **Service discovery** through Eureka for dynamic service location
- **Circuit breaker patterns** should be considered for resilience

### Data Consistency
- **Transactional boundaries** are clearly defined in service layers
- **Read-only transactions** are used for query operations
- **Optimistic locking** may be needed for concurrent updates

### Security Considerations
- **User ID propagation** through HTTP headers (X-User-Id)
- **API key management** for external services through configuration
- **Input validation** using Bean Validation annotations

### Performance Optimization
- **Redis caching** for frequently accessed data
- **Pagination** support for large result sets
- **Connection pooling** for database and external API calls

### Error Handling
- **Custom exceptions** for domain-specific errors
- **Global exception handler** for consistent error responses
- **Structured logging** for debugging and monitoring