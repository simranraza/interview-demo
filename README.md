# Interview Demo

Small Spring Boot demo project that manages Authors and Books. Includes service-layer DTO mapping and unit tests.

## Requirements
- JDK 17+ (or project JDK)
- Maven (or use the included Maven wrapper if present)
- (Optional) VS Code / IntelliJ for development

## Build & run

From project root:

- Using Maven wrapper (Windows)
  mvnw.cmd clean package
  mvnw.cmd spring-boot:run

- Using Maven
  mvn clean package
  mvn spring-boot:run

The application runs on the configured port (default 8080).

## Tests

Run unit tests:

- Windows (wrapper): mvnw.cmd test
- Maven: mvn test

There are unit tests for BookService under src/test/java.

## API (examples)
If the project exposes REST controllers, typical endpoints you can try:

- Create book (example)
  curl -X POST -H "Content-Type: application/json" -d '{"title":"My Book","authorId":1}' http://localhost:8080/api/books

- Get book
  curl http://localhost:8080/api/books/1

- Get books by author
  curl http://localhost:8080/api/books/byAuthor/1
