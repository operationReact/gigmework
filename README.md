# gigmework-spring

This repository contains a basic Spring Boot 3 skeleton for the *gigmework* backend. It defines the core domain entities (Users, Gigs, Proposals) and provides REST endpoints for registering users, logging in, creating gigs and submitting proposals.

## Prerequisites

- Java 17
- Gradle (the wrapper is included)
- PostgreSQL database (configure your connection in `src/main/resources/application.properties`)

## Running locally

1. Clone this repository and navigate into the project directory.
2. Configure your PostgreSQL connection by setting the appropriate `spring.datasource` properties in `src/main/resources/application.properties`.
3. Run the application using the Gradle wrapper:

```sh
./gradlew bootRun
```

The application will start on port 8080 by default. You can test the endpoints using `curl` or Postman:

- **Register a user** (client or freelancer):

  ```sh
  curl -X POST http://localhost:8080/api/auth/register \
       -H 'Content-Type: application/json' \
       -d '{"email":"client@example.com","name":"Client","role":"CLIENT"}'
  ```

- **Log in** (returns the user if found):

  ```sh
  curl -X POST "http://localhost:8080/api/auth/login?email=client@example.com"
  ```

- **Create a gig** (as a client):

  ```sh
  curl -X POST "http://localhost:8080/api/gigs?clientId=1" \
       -H 'Content-Type: application/json' \
       -d '{"title":"Design a logo","description":"Need a new logo","budgetMin":100,"budgetMax":200,"skillsRequired":["design","illustration"]}'
  ```

- **List all gigs**:

  ```sh
  curl http://localhost:8080/api/gigs
  ```

- **Submit a proposal** (as a freelancer):

  ```sh
  curl -X POST "http://localhost:8080/api/gigs/1/proposals?freelancerId=2" \
       -H 'Content-Type: application/json' \
       -d '{"bidAmount":150,"etaDays":7,"coverLetter":"I have 5 years of design experience"}'
  ```

- **List proposals for a gig**:

  ```sh
  curl http://localhost:8080/api/gigs/1/proposals
  ```

## Next steps

This skeleton is intentionally minimal to let you build features incrementally. Suggested improvements:

- Add authentication tokens (JWT) and protect endpoints.
- Add DTO classes to decouple API contracts from persistence entities.
- Introduce service layers for business logic and unit tests.
- Add migrations with Flyway or Liquibase.
- Handle errors and validation more gracefully.