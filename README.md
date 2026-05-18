# README.md

## Todo Management — Spring Boot

Developer quick-start guide for Windows (PowerShell).

## Prerequisites
- Java 8 (OpenJDK/Temurin)
- Maven 3.6+
- MySQL server (or Docker)

## Quick start
1. Run MySQL (Docker):
```powershell
docker run --name todo-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=todo_db -e MYSQL_USER=springuser -e MYSQL_PASSWORD=springpass -p 3306:3306 -d mysql:5.7
```
2. Start app:
```powershell
d C:\Users\MOI\IdeaProjects\todo-management-spring-boot
```
mvn -DskipTests spring-boot:run
3. Access app:
- Login: http://localhost:8080/login
- Home: http://localhost:8080/
- Todos: http://localhost:8080/list-todos
- Register: http://localhost:8080/register
## Default credentials (dev)
- Admin: username `admin`, password `admin`
- MySQL: user `springuser`, password `springpass` at localhost:3306

#Build & run jar
```powershell
mvn -DskipTests clean package
java -jar .\target\todo-management-spring-boot-0.0.1-SNAPSHOT.jar
```
#Run tests
```powershell
mvn test
```
#Key files
- Main: src/main/java/..
    /TodoManagementSpringBoot2Application.java
- Controllers: src/main/java/.../controller/*
- Views: src/main/webapp/WEB-INF/jsp/*
- Config: src/main/resources/application.properties
- DB: src/main/resources/schema.sql, data.sql
- Tests: src/test/java/..
    /TodoRepositoryTest.java, UserServiceTest.java
