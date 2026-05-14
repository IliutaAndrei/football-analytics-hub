# Football Analytics Hub

A comprehensive football analytics backend built with **Spring Boot**, consuming data from the [API-Football](https://www.api-football.com/) external API. This application provides structured access to football data including leagues, teams, players, fixtures, and detailed statistics.

## Tech Stack

- **Java 21**
- **Spring Boot 3.3.5**
- **Spring WebFlux** (WebClient for external API calls)
- **Spring Data JPA** (Hibernate + PostgreSQL)
- **MapStruct** (DTO mapping)
- **Lombok**
- **PostgreSQL**

## Architecture

The application follows a layered architecture with clear separation between external API data and internal models:

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Controller  │────▶│   Service   │────▶│   Mapper    │────▶│  DTO / Entity│
└─────────────┘     └──────┬──────┘     └─────────────┘     └─────────────┘
                           │
                    ┌──────▼──────┐
                    │  API Client  │──── External API (api-football.com)
                    └─────────────┘
```

### Key Design Decisions

- **Static data** (countries, leagues, seasons, teams, players) is persisted in PostgreSQL
- **Dynamic data** (fixtures, statistics, standings) is fetched live from the external API on each request
- **External DTOs** mirror the API-Football JSON structure and live in `client/dto/`
- **Internal DTOs** represent the frontend contract and live in each feature's `dto/` package
- **Internal ↔ External ID translation** is handled in the service layer for persisted entities

## Project Structure

```
org.iliuta.footballhub
├── client/                          # External API communication
│   ├── config/                      #   WebClient configuration
│   ├── dto/                         #   External DTOs (mirrors API-Football JSON)
│   │   ├── countries/
│   │   ├── fixtures/
│   │   │   ├── players/
│   │   │   └── statistics/
│   │   ├── leagues/
│   │   ├── players/
│   │   ├── seasons/
│   │   ├── standings/
│   │   ├── statistics/
│   │   └── teams/
│   └── FootballApiClient.java       #   All external API calls
│
├── countries/                       # Countries feature
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── service/
│   ├── CountryEntity.java
│   └── CountryRepository.java
│
├── leagues/                         # Leagues & Seasons feature
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── service/
│   ├── LeagueEntity.java
│   ├── LeagueRepository.java
│   ├── SeasonEntity.java
│   └── SeasonRepository.java
│
├── teams/                           # Teams feature
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── seasons/                     #   Team seasons sub-feature
│   ├── statistics/                  #   Team statistics sub-feature
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   └── service/
│   ├── service/
│   ├── TeamEntity.java
│   ├── TeamRepository.java
│   ├── VenueEntity.java
│   └── VenueRepository.java
│
├── standings/                       # League standings feature
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   └── service/
│
├── fixtures/                        # Fixtures feature
│   ├── controller/
│   ├── dto/
│   ├── mapper/
│   ├── service/
│   ├── statistics/                  #   Fixture team statistics
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   └── service/
│   └── players/                     #   Fixture player statistics
│       ├── controller/
│       ├── dto/
│       ├── mapper/
│       └── service/
│
├── players/                         # Players feature
│   ├── dto/
│   ├── mapper/
│   ├── squad/                       #   Squad sub-feature (with pagination)
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   └── service/
│   ├── profile/                     #   Player profile sub-feature
│   │   ├── controller/
│   │   └── service/
│   ├── statistics/                  #   Player season statistics
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── mapper/
│   │   └── service/
│   ├── PlayerEntity.java
│   └── PlayerRepository.java
│
└── FootballAnalyticsHubApplication.java
```

## API Endpoints

### Countries
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/countries` | Get all available countries |

### Leagues
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/countries/{countryId}/leagues` | Get leagues for a country |

### Seasons
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/leagues/{leagueId}/seasons` | Get available seasons for a league |

### Teams & Standings
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/leagues/{leagueId}/seasons/{year}/standings` | Get league standings |
| GET | `/api/leagues/{leagueId}/seasons/{year}/teams` | Get teams for a league/season |

### Team Statistics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/teams/{teamId}/statistics` | Get team statistics for a season |

### Fixtures
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/leagues/{leagueId}/seasons/{year}/fixtures` | Get all fixtures for a league/season |

### Fixture Statistics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/fixtures/{fixtureId}/statistics` | Get team statistics for a specific fixture |
| GET | `/api/fixtures/{fixtureId}/players` | Get player statistics for a specific fixture |

### Squad
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/teams/{teamId}/squad` | Get squad for a team (fetches and persists players) |

### Player Profile
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/players/{playerId}/profile` | Get player profile details |

### Player Statistics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/players/{playerId}/statistics` | Get player season statistics (filtered by team and league) |

## Data Model

### Persisted Entities (Static Data)
- **CountryEntity** — country name, code, flag
- **LeagueEntity** — league name, type, logo, linked to country
- **SeasonEntity** — year, start/end dates, linked to league
- **TeamEntity** — team name, logo, founded, linked to league + season + venue
- **VenueEntity** — venue name, city, capacity
- **PlayerEntity** — player profile (name, birth date, nationality, height, weight, position, photo), linked to team

### Live Data (Fetched on Request)
- Standings
- Fixtures
- Fixture Statistics (team-level)
- Fixture Player Statistics
- Player Season Statistics

## Entity Relationships

```
Country ──1:N──▶ League ──1:N──▶ Season
                    │
                    └──1:N──▶ Team ──1:N──▶ Player
                                │
                                └──1:1──▶ Venue
```

## Configuration

### Environment Variables

| Variable | Description |
|----------|-------------|
| `API_FOOTBALL_BASE_URL` | Base URL for API-Football (e.g., `https://v3.football.api-sports.io`) |
| `API_FOOTBALL_KEY` | Your API-Football API key |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |

### WebClient Configuration

The WebClient is configured with a `maxInMemorySize` of **2MB** to handle large API responses (e.g., 380 fixtures per season).

## Getting Started

### Prerequisites
- Java 21
- PostgreSQL
- API-Football API key ([get one here](https://www.api-football.com/))

### Running the Application

1. Clone the repository
2. Configure your database and API key in `application.properties` or via environment variables
3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## License

This project was developed as a bachelor's thesis (licență).
