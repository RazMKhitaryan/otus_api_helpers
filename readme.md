# API Testing Framework

A comprehensive API testing framework built with Java, Spring Boot, and various testing tools.

## Technologies & Frameworks

- Java 21
- Spring Boot 2.7.18
- TestNG 7.11.0
- REST Assured 4.5.1
- Citrus Framework 3.4.1
- Lombok 1.18.38
- ActiveMQ 5.19.0
- RabbitMQ Client 5.16.0
- MySQL Connector 8.0.33

## Project Structure

The project is organized as a Maven project with the following key components:

- `TestBase.java`: Base test class providing Spring context and service autowiring
- `testng.xml`: TestNG configuration for test execution
- Package structure: `wiremocktests.*`

## Features

- Parallel test execution (3 threads)
- Integration with multiple messaging systems (ActiveMQ, RabbitMQ)
- Database connectivity (MySQL)
- HTTP/REST API testing capabilities
- Code quality checks (Checkstyle, SpotBugs)

## Build & Test

To build and run the tests:

```bash
mvn clean test -DthreadCount=5
