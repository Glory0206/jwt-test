# AGENTS.md

This file contains build/lint/test commands and code style guidelines for agentic coding agents working in this Spring Boot Java project.

## Build, Lint, and Test Commands

### Build Commands
```bash
# Build the entire project
./gradlew build

# Compile without running tests
./gradlew compileJava

# Clean build artifacts
./gradlew clean

# Assemble JAR file
./gradlew bootJar
```

### Test Commands
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "kr.java.finalproject.FinalProjectApplicationTests"

# Run tests with detailed output
./gradlew test --info

# Run tests while ignoring failures
./gradlew test --continue

# Generate test reports
./gradlew test jacocoTestReport
```

### Development Commands
```bash
# Run the application
./gradlew bootRun

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'

# Check for dependency updates
./gradlew dependencyUpdates
```

## Code Style Guidelines

### Project Structure
- Base package: `kr.java.finalproject`
- Domain-driven structure: `domain/{module}/{layer}`
  - `controller/` - REST controllers
  - `service/` - Business logic
  - `repository/` - Data access layer
  - `entity/` - JPA/Redis entities
  - `dto/` - Data transfer objects
- Global utilities: `global/{module}`

### Import Organization
1. Java standard libraries (`java.*`)
2. Third-party libraries (`org.springframework.*`, `lombok.*`)
3. Project imports (`kr.java.finalproject.*`)
4. Static imports (if used)

Use single wildcard imports per package, avoid multiple wildcards.

### Code Formatting
- Use 4 spaces for indentation (no tabs)
- Maximum line length: 120 characters
- Place opening braces on the same line for methods and classes
- Use new line for opening braces in control structures (if/for/while)

### Naming Conventions
- **Classes**: PascalCase (`HomeController`, `RefreshToken`)
- **Methods**: camelCase (`updateRefreshToken`, `filterChain`)
- **Variables**: camelCase (`jwtProvider`, `oAuth2SuccessHandler`)
- **Constants**: UPPER_SNAKE_CASE (`TIME_TO_LIVE_SECONDS`)
- **Packages**: lowercase with dots (`kr.java.finalproject.domain.auth`)
- **Files**: match class name exactly

### Annotations Usage
- **Lombok**: Use `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` for POJOs
- **Spring**: Use `@RestController`, `@Service`, `@Repository`, `@Component` appropriately
- **Configuration**: Use `@Configuration`, `@Bean`, `@EnableWebSecurity`
- **Security**: Use `@RequiredArgsConstructor` for dependency injection

### Error Handling
- Use Spring's `@ExceptionHandler` in `@ControllerAdvice` for global exception handling
- Create custom exceptions extending `RuntimeException`
- Use proper HTTP status codes in responses
- Log errors appropriately using SLF4J

### Security Best Practices
- All endpoints should be authenticated except `/`, `/login/**`, `/error`
- Use JWT tokens with proper expiration
- Store refresh tokens in Redis with TTL
- Validate all inputs and sanitize user data
- Use `@PreAuthorize` for method-level security when needed

### Database Guidelines
- Use `@RedisHash` for Redis entities with appropriate TTL
- Use `@Id` for primary keys
- Use `@Builder` pattern for entity creation
- Implement proper equals/hashCode when needed
- Use `update*` methods for entity updates instead of setters

### Testing Guidelines
- Unit tests should be in `src/test/java` following the same package structure
- Use `@SpringBootTest` for integration tests
- Use `@MockBean` for mocking Spring components
- Test name should follow `methodName_condition_expectedResult` pattern
- Keep tests isolated and independent

### Dependencies and Libraries
- Spring Boot 4.0.2 with Spring Security
- Lombok for reducing boilerplate
- JJWT (JSON Web Token) for JWT handling
- Spring Data Redis for token storage
- JUnit Platform for testing

### Configuration Management
- Use `application.yml` for configuration
- Use profiles (`dev`, `prod`) for environment-specific settings
- Externalize secrets to environment variables
- Use `@ConfigurationProperties` for complex configuration

### Performance Considerations
- Use `@RequiredArgsConstructor` instead of field injection
- Cache frequently accessed data
- Use connection pooling for database operations
- Implement proper pagination for large datasets
- Consider async processing for long-running operations

### Common Patterns
- **Controller**: Handle HTTP requests, delegate to services
- **Service**: Business logic, transaction management
- **Repository**: Data access, CRUD operations
- **DTO**: Data transfer between layers
- **Entity**: Data model representation

### Git and Commit Guidelines
- Conventional commit messages: `type(scope): description`
- Types: `feat`, `fix`, `refactor`, `test`, `docs`, `style`, `chore`
- Keep commits focused and atomic
- Write clear, descriptive commit messages

## Quick Start for New Agents

1. Check Java version: `java -version` (requires Java 17)
2. Build project: `./gradlew build`
3. Run tests: `./gradlew test`
4. Start application: `./gradlew bootRun`
5. Access application at: `http://localhost:8080`

When adding new features:
1. Create domain structure under `domain/`
2. Follow existing patterns for controllers, services, repositories
3. Add appropriate tests
4. Update configuration if needed
5. Run full test suite before committing