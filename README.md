Football Analytics Hub is a scalable backend application built with Java 21 and Spring Boot 3, designed to aggregate, process, and expose structured football data through a clean RESTful API.

The system integrates with an external football data provider (API-Football) and transforms raw API responses into a well-defined internal domain model using a layered architecture and DTO-based mapping. The goal of the project is to build a production-ready, extensible backend platform that can support real-time data visualization, historical analysis, and future predictive analytics.

Architecture

The project follows a feature-based package structure and a clean layered architecture:

Controller Layer – exposes RESTful endpoints

Service Layer – contains business logic and data orchestration

Client Layer – handles external API communication via WebClient

Mapper Layer – DTO transformation using MapStruct

DTO Model – clear separation between external and internal representations

Key architectural principles:

Separation of concerns

DTO-based data isolation

Clean API contracts

Extensibility for future features

Current Features

League retrieval by country

Teams by league and season

Team seasons history

League standings (flattened and structured)

Team statistics (goals, fixtures, performance metrics)

Fixtures by team, league, and season (grouped into played and upcoming)

Planned Extensions

Match events (goals, cards, substitutions)

Data persistence layer (PostgreSQL integration)

Historical data synchronization

Caching strategies

Advanced analytics and predictive modeling

Frontend integration (React-based UI)

Tech Stack

Java 21

Spring Boot 3

Spring Web

WebClient

MapStruct

Lombok

Maven

Project Goal

The objective of this project is to simulate a real-world, enterprise-style backend system capable of handling structured sports data while maintaining clean architecture, scalability, and maintainability standards suitable for production environments.
