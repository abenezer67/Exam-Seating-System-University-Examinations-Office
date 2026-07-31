# University Exam Seating Arrangement System

A Spring Boot application that assigns students to exam room seats. It has two parts:
a **console admin tool** for quickly adding students/rooms at startup, and a **web app**
(REST API + a static HTML dashboard) for managing students, rooms, and generating/viewing
the seating arrangement.

## Features

- Add / remove students (ID, name, department)
- Add / remove exam rooms (ID, seat capacity)
- Randomly generate a seating arrangement across all rooms
- View the allocation table and a visual seating chart in the browser
- Data is persisted to a SQL Server database via Spring Data JPA

## Tech Stack

- Java 17
- Spring Boot 4.1.0 (Web MVC + Data JPA)
- Microsoft SQL Server (via `mssql-jdbc`)
- Maven (with the included Maven Wrapper, so a local Maven install isn't required)
- Plain HTML/CSS/JS front end (`src/main/resources/static/index.html`)

## Prerequisites

1. **Java 17** (or newer) installed — check with `java -version`
2. **A running SQL Server instance**, reachable at `localhost:1433`. This can be:
   - A local SQL Server / SQL Server Express install, or
   - The official Docker image, e.g.:
     ```bash
     docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=YourStrong!Passw0rd" \
       -p 1433:1433 --name exam-seating-sql -d mcr.microsoft.com/mssql/server:2022-latest
     ```
3. A database named `exam_seating_db`. You don't need to create the tables yourself —
   Hibernate will create/update them automatically on startup (`spring.jpa.hibernate.ddl-auto=update`),
   but the **database itself** needs to exist first. Connect with your preferred SQL client
   (Azure Data Studio, SSMS, `sqlcmd`, etc.) and run:
   ```sql
   CREATE DATABASE exam_seating_db;
   ```

## Configuration

Database connection settings live in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=exam_seating_db;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=<your-password>
```

Update the `username`/`password` (and `url` if your SQL Server isn't on `localhost:1433`)
to match your own setup before running the app.

> **Security note:** the current file has a real password committed in plain text. That's
> fine for local coursework, but if you push this repo to GitHub, either change the password
> to a placeholder or move it to an environment variable so real credentials never end up in
> version control.

## Running the Project

### Option A — Using the Maven Wrapper (recommended, no Maven install needed)

From the `demo/` folder:

```bash
# macOS/Linux
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

### Option B — Using an installed Maven

```bash
mvn spring-boot:run
```

### Option C — From an IDE (IntelliJ / Eclipse / VS Code)

Import `demo/` as a Maven project, then run `DemoApplication.java` directly.

### What happens on startup

1. The **console admin tool** runs first in the terminal:

   ```
   ========================================
    Exam Seating System - Console Admin Tool
   ========================================

   1. Add Student
   2. Add Room
   3. Continue to Web Server
   Choose an option:
   ```

   Use options `1` and `2` to seed some students/rooms if you like, or just pick `3` to
   skip straight to the web server.

2. Once you choose option `3`, the Spring Boot web server starts on the default port.

## Using the App

- **Web dashboard:** open [http://localhost:8080](http://localhost:8080) in your browser.
  It has four tabs: **Home**, **Students**, **Rooms**, and **Seating**, where you can
  register/remove students and rooms, generate the arrangement, and view the allocation
  table and visual seating chart.
  - Login with the Admin Password and username:
    Username-> admin
    Password-> admin123

- **REST API** (used by the dashboard, but callable directly too):

  | Method | Endpoint            | Description                                                  |
  | ------ | ------------------- | ------------------------------------------------------------ |
  | POST   | `/api/student`      | Add a student (JSON body: `studentId`, `name`, `department`) |
  | DELETE | `/api/student/{id}` | Remove a student by ID                                       |
  | POST   | `/api/room`         | Add a room (JSON body: `roomId`, `capacity`)                 |
  | DELETE | `/api/room/{id}`    | Remove a room by ID                                          |
  | POST   | `/api/allocate`     | Randomly generate the seating arrangement                    |
  | GET    | `/api/allocations`  | Get the current allocation table                             |

  Example:

  ```bash
  curl -X POST http://localhost:8080/api/student \
    -H "Content-Type: application/json" \
    -d '{"studentId":"S001","name":"Jane Doe","department":"CS"}'
  ```

## Project Structure

```
demo/
├── mvnw, mvnw.cmd          # Maven wrapper scripts
├── pom.xml                 # Maven build file / dependencies
└── src/
    ├── main/
    │   ├── java/com/example/demo/
    │   │   ├── DemoApplication.java        # Spring Boot entry point
    │   │   ├── console/ConsoleAdminTool.java   # Startup console menu
    │   │   ├── controller/WebController.java   # REST API endpoints
    │   │   ├── service/SeatingManagerService.java  # Core business logic
    │   │   ├── repository/                 # Spring Data JPA repositories
    │   │   └── model/                      # Person, Student, Room, Allocation entities
    │   └── resources/
    │       ├── application.properties      # DB connection config
    │       └── static/index.html           # Web dashboard
    └── test/                               # Unit tests
```

## Troubleshooting

- **App fails to start with a connection error:** confirm SQL Server is running and
  reachable on the host/port in `application.properties`, and that `exam_seating_db` exists.
- **Login failed for user 'sa':** double-check the username/password in
  `application.properties` match your SQL Server instance.
- **Port 8080 already in use:** stop whatever else is using it, or add
  `server.port=8081` (or another free port) to `application.properties`.
# Exam-Seating-System-University-Examinations-Office
