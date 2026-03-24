# Inventory Service - Product CRUD API

A Spring Boot REST API service for managing pharmaceutical products in a drug store inventory system. Provides complete CRUD operations with PostgreSQL database persistence and Swagger UI documentation.

## Quick Start

### Prerequisites
- Java 25 or higher
- PostgreSQL 12 or higher
- Gradle (included via gradlew wrapper)

### Setup Steps

1. Create PostgreSQL Database
   ```bash
   psql -U postgres
   CREATE DATABASE inventory_db;
   \q
   ```

2. Start the Application
   ```bash
   cd /Users/aliciazamorano/Documents/InventoryService
   ./gradlew bootRun
   ```

3. Verify Application is Running
   ```bash
   curl http://localhost:8080/api/products
   # Should return: []
   ```

## Requirements

### System Requirements
- Operating System: macOS, Linux, or Windows
- Java Version: 25+
- PostgreSQL: 12+
- Memory: 512MB minimum, 1GB recommended
- Disk Space: 100MB for application + database

### Database Requirements
- PostgreSQL server running
- Database: inventory_db
- User: postgres (default)
- Password: postgres (default - change for production)

## Dependencies

The application uses the following key dependencies:

### Core Framework
- Spring Boot: 4.0.3 - Main framework
- Spring Web: REST API support
- Spring Data JPA: Database operations
- Hibernate: ORM implementation

### Database
- PostgreSQL Driver: 42.7.1 - Database connectivity
- H2 Database: In-memory database for testing

### Development Tools
- Lombok: Reduces boilerplate code
- Spring Boot DevTools: Development utilities

### API Documentation
- SpringDoc OpenAPI: 2.6.0 - Swagger UI generation

### Testing
- JUnit 5: Unit testing framework
- Mockito: Mocking framework
- Spring Test: Integration testing

## API Documentation (Swagger)

The API includes comprehensive Swagger UI documentation for interactive testing and exploration.

### Access Swagger UI
- URL: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

### Features
- Interactive API testing
- Request/response examples
- Schema definitions
- Authentication documentation (when implemented)

## API Endpoints

Base URL: http://localhost:8080/api/products

### 1. Create Product
```bash
POST /api/products
Content-Type: application/json

{
  "sku": "ASP-001",
  "barCode": "1234567890123",
  "productName": "Aspirin 100mg",
  "productDescription": "Pain relief medication",
  "category": "MEDICINE",
  "prescriptionRequired": false,
  "controlledSubstance": false,
  "sanitaryRegistration": "SR-ASP-001",
  "reorderLevel": 50
}
```

Response: 201 Created with created product data

### 2. Get All Products
```bash
GET /api/products
```

Response: 200 OK with array of products

### 3. Get Product by ID
```bash
GET /api/products/{id}
# Replace {id} with actual UUID
```

Response: 200 OK with product data or 404 Not Found

### 4. Get Product by SKU
```bash
GET /api/products/sku/{sku}
# Example: GET /api/products/sku/ASP-001
```

Response: 200 OK with product data or 404 Not Found

### 5. Get Product by Sanitary Registration
```bash
GET /api/products/sanitary/{sanitaryRegistration}
# Example: GET /api/products/sanitary/SR-ASP-001
```

Response: 200 OK with product data or 404 Not Found

### 6. Update Product
```bash
PUT /api/products/{id}
Content-Type: application/json

{
  "productName": "Updated Aspirin 100mg",
  "productDescription": "Updated description"
}
```

Response: 200 OK with updated product or 404 Not Found

### 7. Delete Product
```bash
DELETE /api/products/{id}
```

Response: 204 No Content or 404 Not Found

## Testing

### Run All Tests
```bash
./gradlew test
```

### Run Specific Tests
```bash
# Service layer tests
./gradlew test --tests ProductServiceTest

# Controller integration tests
./gradlew test --tests ProductControllerTest

# Application context test
./gradlew test --tests InventoryServiceApplicationTests
```

### Build and Test
```bash
./gradlew clean build
```

## Project Structure

```
InventoryService/
├── src/
│   ├── main/
│   │   ├── java/org/azamorano/inventoryservice/
│   │   │   ├── InventoryServiceApplication.java
│   │   │   ├── config/
│   │   │   │   └── OpenApiConfig.java
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java
│   │   │   ├── entity/
│   │   │   │   ├── Product.java
│   │   │   │   ├── ProductCategory.java
│   │   │   │   └── [other entities...]
│   │   │   ├── repository/
│   │   │   │   └── ProductRepository.java
│   │   │   └── service/
│   │   │       └── ProductService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/org/azamorano/inventoryservice/
│       │   ├── controller/
│       │   │   └── ProductControllerTest.java
│       │   ├── service/
│       │   │   └── ProductServiceTest.java
│       │   └── InventoryServiceApplicationTests.java
│       └── resources/
│           └── application.properties
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

## Configuration

### Main Application (application.properties)
```properties
spring.application.name=InventoryService
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

### Test Configuration (test/application.properties)
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Production Deployment

Before deploying to production:

1. Change Database Password
   - Update application.properties with strong credentials

2. Update DDL Auto
   - Change spring.jpa.hibernate.ddl-auto=validate

3. Implement Database Migrations
   - Use Flyway or Liquibase for schema versioning

4. Add Security
   - Implement JWT authentication
   - Add HTTPS/TLS
   - Configure CORS

5. Monitoring & Logging
   - Add application monitoring
   - Configure structured logging
   - Set up error tracking

## Troubleshooting

### Application Won't Start
- Check Java version: java -version
- Verify PostgreSQL is running
- Check database connection in application.properties

### Port 8080 Already in Use
- Kill existing process: lsof -ti:8080 | xargs kill -9
- Or change port: Add server.port=8081 to properties

### Database Connection Issues
- Ensure PostgreSQL service is running
- Verify database inventory_db exists
- Check username/password in properties

### Tests Failing
- Run with verbose output: ./gradlew test --info
- Clear Gradle cache: ./gradlew clean
- Check H2 database configuration

## Features

- Complete CRUD operations
- RESTful API design
- PostgreSQL integration
- UUID primary keys
- Input validation
- Error handling
- Transaction management
- Entity relationships
- Comprehensive testing (32 tests)
- Swagger UI documentation
- SOLID principles
- Clean code practices

## Contributing

1. Follow Java coding standards
2. Write tests for new features
3. Update documentation
4. Ensure all tests pass

## License

This project is part of the Inventory Service implementation.
