# Todo Management Spring Boot

A Spring Boot web application for managing todos, tags, subtasks, users, and authentication with JSP views and MySQL.

## What the system does

- Users register, log in, and manage their own todo list.
- Todos support:
  - description
  - target date
  - priority
  - status
  - tags
  - subtasks
- Subtasks belong to a parent todo and can be marked independently.
- The app uses Spring Security for login/logout and role-based access.
- The UI is rendered with JSP pages under `src/main/webapp/WEB-INF/jsp/`.

## Tech stack

- Java 8
- Spring Boot 2.0.4
- Spring MVC + JSP
- Spring Data JPA / Hibernate
- Spring Security
- MySQL 8 via XAMPP
- Bootstrap 3 + Font Awesome

## Database setup with XAMPP

The app expects MySQL to be available on `localhost:3306` and uses the database below:

- Database name: `todo_db`
- Username: `springuser`
- Password: `springpass`

These values are configured in `src/main/resources/application.properties`.

If your local MySQL user/password is different, update the datasource properties before launching the app.

## How the app is structured

- `src/main/java/.../controller/` — request handlers for home, todos, users, tags, subtasks, auth.
- `src/main/java/.../service/` — business logic and status updates.
- `src/main/java/.../repository/` — JPA repositories.
- `src/main/java/.../model/` — entities like `Todo`, `Tag`, `Subtask`, and `User`.
- `src/main/webapp/WEB-INF/jsp/` — JSP pages for the UI.
- `src/main/resources/application.properties` — datasource and view resolver settings.

## Run the project with Maven

From the project root:

```powershell
mvn clean package -DskipTests
```

Then run the application with:

```powershell
mvn -DskipTests spring-boot:run
```

Or, if you already built the project and just want to start it:

```powershell
java -jar .\target\todo-management-spring-boot-0.0.1-SNAPSHOT.jar
```

## Run the packaged JAR

If the JAR already exists in `target/`, start it directly:

```powershell
java -jar .\target\todo-management-spring-boot-0.0.1-SNAPSHOT.jar
```

## Useful URLs

- Home: `http://localhost:8080/`
- Login: `http://localhost:8080/login`
- Register: `http://localhost:8080/register`
- Todos: `http://localhost:8080/list-todos`
- Profile: `http://localhost:8080/profile`

## Default development credentials

If the demo data is loaded, the sample admin account is typically:

- Username: `admin`
- Password: `admin`

## Troubleshooting

- **Cannot connect to database**: confirm MySQL is running and the `todo_db` database exists.
- **Port 8080 already in use**: stop the app or free the port before starting again.
- **JSP changes do not appear**: rebuild with Maven and restart the app.
- **Old build artifacts cause issues**: delete the `target/` folder and rebuild.

## Quick command reference

```powershell
mvn clean package -DskipTests
java -jar .\target\todo-management-spring-boot-0.0.1-SNAPSHOT.jar
```
