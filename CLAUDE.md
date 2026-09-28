# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Spring Boot REST API for managing a pharmaceutical drug store inventory. Java 25, Spring Boot 4.0.3, Gradle, PostgreSQL (Flyway-migrated) in production, H2 in tests. Package root: `org.azamorano.inventoryservice`.

## Commands

```bash
./gradlew bootRun          # Run locally (needs PostgreSQL on localhost:5432, db inventory_db)
./gradlew clean build      # Full build + tests
./gradlew test             # Run all tests
./gradlew bootJar          # Build the runnable jar (build/libs/*.jar)

# Run a single test class or method
./gradlew test --tests "org.azamorano.inventoryservice.service.ProductServiceTest"
./gradlew test --tests "*ProductServiceTest.shouldCreateProduct"

# Docker (uses prod profile + env vars POSTGRES_DB/USER/PASSWORD)
docker compose up -d --build
```

Test reports: `build/reports/tests/test/index.html`. API runs on port 8080; Swagger UI at `/swagger-ui.html`, OpenAPI at `/v3/api-docs`.

## Architecture

Standard layered flow, one set of classes per entity, kept in strict parallel:

```
controller/  →  service/  →  repository/   (Spring Data JPA)
     ↕                            ↕
  mapper/                      entity/
 dto/request, dto/response
```

- **Controllers** speak only DTOs. They map inbound `*RequestDto` → entity, call the service, and map the result → `*ResponseDto`. They do NOT return entities directly.
- **Mappers** (`mapper/`) are hand-written `@Component` classes (no MapStruct). Each has `toEntity`, `toResponseDto`, `toResponseDtoList`. When adding/renaming an entity field, update the corresponding mapper by hand — nothing is generated.
- **Services** hold business rules and validation, are `@Transactional`, and use constructor injection.
- **Entities** use Lombok `@Data`/`@NoArgsConstructor`/`@AllArgsConstructor`, UUID primary keys (`GenerationType.AUTO`). `Product` is the aggregate root with `@OneToMany` cascade collections (presentations, batches, prices, therapeutic actions, active ingredients) and `@ManyToOne` refs to `ProductCategory`, `Laboratory`, `Brand`.

When adding a new entity, replicate the full set: entity, repository, service, mapper, controller, request DTO, response DTO — following an existing one (e.g. `ActiveIngredient`) as the template.

### Error handling (important — no global handler)
There is no `@ControllerAdvice`. Services throw `IllegalArgumentException` for both validation failures and "not found". Controllers `try/catch` `IllegalArgumentException` and translate it to `404 Not Found` (or rely on Spring's default `400` for bean-validation failures). Follow this existing pattern rather than introducing a new exception strategy unless asked.

### Product creation business rules (see `ProductService`)
- `ProductCategory` must already exist — it is looked up by name; a missing category throws.
- `Laboratory` must already exist when provided — looked up by name; missing throws.
- `Brand` is optional and is created/saved on the fly if provided.
- `update` is a partial/patch-style merge: only non-null (and `reorderLevel > 0`) fields overwrite.

## Database & migrations

- Production schema is managed by **Flyway** migrations in `src/main/resources/db/migration/` (`V1__initial_schema.sql`, etc.). `application.properties` has NO `ddl-auto` — do not add one for prod; add a new versioned `V*__*.sql` migration instead. (The README's mention of `ddl-auto=create` is outdated.)
- Migration file naming convention in use is lowercase after the double underscore: `V5__add_something.sql`.
- **Tests** (`src/test/resources/application-test.properties`) use in-memory H2 with `ddl-auto=create-drop` — Hibernate builds the schema, Flyway migrations are not the source of truth there. Tests must not depend on Postgres-only SQL.

## Testing conventions

- Unit tests use JUnit 5 + Mockito (`@ExtendWith(MockitoExtension.class)`, `@Mock` dependencies) — services tested with mocked repositories, no Spring context.
- Controller tests use the Spring test/MVC slice.

## Gotchas

- CI `.github/workflows/build.yml` runs `./mvnw clean verify` (Maven) but this project is **Gradle-only** — there is no Maven wrapper. This CI step is broken; use Gradle commands.
- There is a stray/misnamed file `src/main/resources/application-prod_01.properties_01` (not a real profile). The Docker setup activates `SPRING_PROFILES_ACTIVE=prod`.
