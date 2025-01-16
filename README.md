 # Clean Architecture Archetype for Java Spring Boot Applications

This project is a Java Spring Boot application that demonstrates clean architecture principles and provides a robust foundation for building scalable and maintainable web services.

The Clean Architecture Archetype is designed to help developers quickly set up a project structure that adheres to clean architecture principles, promoting separation of concerns and modularity. It includes a sample implementation of a joke retrieval service and user management functionality to showcase the architecture's capabilities.

## Repository Structure

```
.
├── application
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── pragma
│                       └── clean_architecture
│                           ├── CleanArchitectureApplication.java
│                           └── config
│                               ├── SwaggerConfig.java
│                               └── UseCaseConfig.java
├── domain
│   ├── pom.xml
│   └── src
│       ├── main
│       │   └── java
│       │       └── com
│       │           └── pragma
│       │               └── clean_architecture
│       │                   ├── driven_port
│       │                   ├── model
│       │                   └── use_case
│       └── test
│           └── java
│               └── com
│                   └── pragma
│                       └── clean_architecture
│                           └── use_case
├── infrastructure
│   ├── pom.xml
│   └── src
│       ├── main
│       │   └── java
│       │       └── com
│       │           └── pragma
│       │               └── clean_architecture
│       │                   ├── driven_adapters
│       │                   ├── entry_points
│       │                   └── helpers
│       └── test
│           ├── java
│           │   └── com
│           │       └── pragma
│           │           └── clean_architecture
│           └── resources
│               └── mock
├── pom.xml
├── Dockerfile
└── README.md
```

Key Files:
- `application/src/main/java/com/pragma/clean_architecture/CleanArchitectureApplication.java`: Main entry point of the application
- `application/src/main/java/com/pragma/clean_architecture/config/SwaggerConfig.java`: Swagger configuration for API documentation
- `domain/src/main/java/com/pragma/clean_architecture/use_case/`: Contains use case implementations
- `infrastructure/src/main/java/com/pragma/clean_architecture/driven_adapters/`: Contains adapters for external services and databases
- `infrastructure/src/main/java/com/pragma/clean_architecture/entry_points/rest/controller/`: Contains REST controllers
- `pom.xml`: Root Maven configuration file
- `Dockerfile`: Docker configuration for containerization

## Hexagonal Architecture

This project implements the Hexagonal Architecture, also known as Ports and Adapters pattern. Hexagonal Architecture is an architectural pattern that allows an application to be equally driven by users, programs, automated tests, or batch scripts, and to be developed and tested in isolation from its eventual run-time devices and databases.

Key principles of Hexagonal Architecture:

1. Separation of Concerns: The application is divided into layers, with the domain logic at the core.
2. Dependency Inversion: Dependencies point inwards, with the domain layer having no dependencies on outer layers.
3. Ports and Adapters: The application defines ports (interfaces) that are implemented by adapters in the outer layers.

In this project, the Hexagonal Architecture is implemented as follows:

- Domain Layer (`domain` module): Contains the core business logic, use cases, and domain models.
- Application Layer (`application` module): Orchestrates the flow of data to and from the domain layer.
- Infrastructure Layer (`infrastructure` module): Contains the implementations of the ports defined in the domain layer, including REST controllers, database adapters, and external service clients.

This architecture allows for easy swapping of infrastructure components without affecting the core business logic, promoting maintainability and testability.

## Multi-Module Maven Project

This project is structured as a multi-module Maven project, which is a best practice for organizing large Java applications. A multi-module project consists of a parent project and several child modules, each with its own `pom.xml` file.

Benefits of using a multi-module Maven project:

1. Modularity: The application is divided into logical modules, each with a specific responsibility.
2. Dependency Management: The parent POM can manage dependencies and versions for all modules.
3. Build Optimization: Maven can build modules in parallel and skip unchanged modules.
4. Code Organization: Helps in organizing code based on layers or features.

In this project, we have three main modules:

1. `application`: Contains the main application configuration and entry point.
2. `domain`: Contains the core business logic and domain models.
3. `infrastructure`: Contains the implementation of adapters and infrastructure concerns.

This structure aligns well with the Hexagonal Architecture, separating concerns and allowing for independent development and testing of each module.

## Usage Instructions

### Installation

Prerequisites:
- Java 17
- Maven 3.6+
- Docker (optional, for containerization)

To install the project, follow these steps:

1. Clone the repository:
   ```
   git clone <repository-url>
   cd clean-architecture-archetype
   ```

2. Build the project:
   ```
   mvn clean install
   ```

### Getting Started

To run the application locally:

1. Start the application:
   ```
   java -jar application/target/clean-architecture-archetype.jar
   ```

2. The application will start on `http://localhost:8080`

3. Access the Swagger UI for API documentation:
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Configuration

The application can be configured using environment variables or by modifying the `application.properties` file in the `application` module.

Key configuration options:
- `server.port`: The port on which the application runs (default: 8080)
- `spring.datasource.url`: Database connection URL
- `spring.datasource.username`: Database username
- `spring.datasource.password`: Database password

### Common Use Cases

1. Retrieving a random joke:
   ```
   GET /joke/random
   ```

   Example response:
   ```json
   {
     "id": "123",
     "value": "Chuck Norris can divide by zero.",
     "categories": ["science"]
   }
   ```

2. Creating a new user:
   ```
   POST /users/save
   Content-Type: application/json

   {
     "name": "John Doe",
     "email": "john.doe@example.com",
     "address": "123 Main St"
   }
   ```

   Example response:
   ```json
   {
     "id": 1,
     "name": "John Doe",
     "email": "john.doe@example.com",
     "address": "123 Main St"
   }
   ```

3. Retrieving all users:
   ```
   GET /users/all
   ```

   Example response:
   ```json
   [
     {
       "id": 1,
       "name": "John Doe",
       "email": "john.doe@example.com",
       "address": "123 Main St"
     },
     {
       "id": 2,
       "name": "Jane Smith",
       "email": "jane.smith@example.com",
       "address": "456 Elm St"
     }
   ]
   ```

### Testing & Quality

To run tests and generate a code coverage report:

```
mvn clean verify
```

The JaCoCo code coverage report will be generated in the `target/site/jacoco` directory of each module.

### Troubleshooting

1. Issue: Application fails to start
   - Error message: "Port 8080 is already in use"
   - Diagnostic process:
     1. Check if another application is using port 8080
     2. Use the command `lsof -i :8080` to identify the process
   - Solution: Either stop the conflicting process or change the port in `application.properties`:
     ```
     server.port=8081
     ```

2. Issue: Database connection fails
   - Error message: "Could not create connection to database server"
   - Diagnostic process:
     1. Verify database credentials in `application.properties`
     2. Ensure the database server is running and accessible
   - Solution: Update database configuration in `application.properties`:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

### Debugging

To enable debug mode, add the following VM argument when starting the application:

```
java -Dlogging.level.root=DEBUG -jar application/target/clean-architecture-archetype.jar
```

Debug logs will be available in the console output and in the log file located at `logs/application.log`.

### Performance Optimization

#### Fault Tolerance Patterns with Resilience4j

The application implements several fault tolerance patterns using the Resilience4j library to ensure system reliability and stability:

1. **Circuit Breaker Pattern**
   - Prevents system overload by temporarily stopping calls to failing services
   - Configurations in `application.yml` control failure thresholds and recovery behavior
   - Applied to external API calls in `ChuckNorrisApiPortImpl`
   - Includes fallback methods for graceful degradation

2. **Rate Limiter Pattern**
   - Controls the rate of requests to protect services from overload
   - Configurable limits for requests per time period
   - Helps maintain service stability under high load

3. **Bulkhead Pattern**
   - Isolates different parts of the system to prevent cascade failures
   - Limits concurrent calls to services
   - Prevents single component failures from affecting entire system

4. **Retry Pattern**
   - Automatically retries failed operations
   - Configurable retry attempts and wait durations
   - Handles transient failures gracefully

Configuration is maintained in `application.yml` with detailed comments for each setting.
Implementation can be found in `ChuckNorrisApiPortImpl` using annotations:
- @CircuitBreaker
- @RateLimiter
- @Bulkhead
- @Retry

To monitor application performance:
1. Enable Spring Boot Actuator by adding the following dependency to the `application/pom.xml`:
   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   ```
2. Access performance metrics at `http://localhost:8080/actuator/metrics`
3. Use tools like VisualVM or JConsole to profile the application

Common bottlenecks and solutions:
- High database query times: Optimize queries and add appropriate indexes
- High memory usage: Analyze heap dumps to identify memory leaks
- Slow external API calls: Implement caching or circuit breakers using libraries like Resilience4j

## Data Flow

The application follows a clean architecture pattern, separating concerns into distinct layers. Here's an overview of the data flow:

1. HTTP Request → Entry Points (REST Controllers)
2. Controllers → Use Cases (Application Services)
3. Use Cases → Domain Models and Driven Ports (Interfaces)
4. Driven Adapters (Implementations) ← Driven Ports
5. Driven Adapters → External Systems (Database, APIs)

```
[Client] → [Entry Points] → [Use Cases] → [Domain Models]
                ↑               ↑               ↑
                |               |               |
                └───[Driven Adapters]───────────┘
                            ↓
                    [External Systems]
```

Notes:
- The domain layer (use cases and models) has no dependencies on external frameworks or systems
- Driven adapters implement the interfaces (ports) defined in the domain layer
- Data flows inward, with outer layers depending on inner layers, but not vice versa

## Deployment

Prerequisites:
- Docker
- Docker Compose (optional, for local deployment)

Deployment steps:

1. Build the Docker image:
   ```
   docker build -t clean-architecture-archetype .
   ```

2. Run the Docker container:
   ```
   docker run -p 8080:8080 clean-architecture-archetype
   ```

3. The application will be accessible at `http://localhost:8080`

For production deployments, consider using container orchestration platforms like Kubernetes or AWS ECS for better scalability and management.

## Infrastructure

The project uses a Dockerfile for containerization. Key resources defined in the Dockerfile include:

- Base Image: Amazon Corretto 17 Alpine JDK
- Exposed Port: 8080
- Entry Point: Java application JAR file

The infrastructure also includes:

- JPA Configuration: Enables JPA auditing and repository scanning
- User Repository: JPA repository for user entity persistence