# To-Do List Application

A Java EE 8 task management web application. Create multiple task lists, add items, mark them complete, update list names, and delete lists, all persisted in MySQL via JPA/Hibernate.

## Features

- Create, rename, and delete task lists
- Add task items to any list, toggle completion, or delete them
- Delete warning: lists with incomplete tasks show a confirmation dialog
- REST API for async delete + incomplete-task check

## Tech Stack

| Category     | Technology                    |
|-------------|-------------------------------|
| Language     | Java 11                       |
| Build        | Maven (WAR packaging)         |
| App Server   | WildFly 17.0.1.Final (JBoss) |
| Database     | MySQL 8                       |
| ORM          | Hibernate (via WildFly's bundled 5.3) |
| JPA          | JPA 2.0 (persistence.xml + orm.xml) |
| Web          | Servlet 4.0, JSP 2.3, JSTL   |
| REST         | JAX-RS 2.x (RESTEasy)        |
| DI           | EJB (`@Stateless`, `@EJB`)    |
| Frontend     | Vanilla JavaScript, CSS       |

## Prerequisites

- **JDK 11** 
- **Maven 3.6+**
- **MySQL 8.x** — running on `localhost:3306`
- **WildFly 17.0.1.Final** — extracted to `C:\wildfly-17.0.1.Final`

> The project assumes WildFly is at `C:\wildfly-17.0.1.Final`. If yours is elsewhere, set the `JBOSS_HOME` environment variable.

## Architecture

```
Browser (JSP + JS + CSS)
    |
Servlet (TodoServlet)  ──→  REST API (JAX-RS)
    |                            |
Service (EJB @Stateless)    TaskListResource
    |                         (GET / DELETE)
Repository (EJB + EntityManager)
    |
JPA / Hibernate
    |
MySQL (TaskDB)
```

Layered: **Controller** → **Service** (interface + impl) → **Repository** → **JPA EntityManager** → **MySQL**

## API Endpoints

| Method | Endpoint                                          | Description                          |
|--------|--------------------------------------------------|--------------------------------------|
| GET    | `/to_do_list/api/tasklist/{id}/hasIncompleteTasks` | Returns `{"hasIncompleteTasks": true/false}` |
| DELETE | `/to_do_list/api/tasklist/{id}`                    | Deletes a task list (204 on success, 404 if not found) |

## Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven build: dependencies, plugins, WAR output to `${JBOSS_HOME}/standalone/deployments` |
| `persistence.xml` | JPA persistence unit `SquirrelPU` — JNDI datasource `java:/TaskDB_MySQLDS`, MySQL8 dialect |
| `orm.xml` | Sets default schema to `TaskDB` for all entity tables |
| `web.xml` | Servlet deployment descriptor (annotations handle most config) |
| `schema.sql` | Reference DDL — run manually to create tables |
| `standalone.xml` | WildFly server config — contains the datasource definition |

## How to Run

1. Download & install the prerequisites: JDK 11, Maven, MySQL 8, and WildFly 17.0.1.Final (extract to `C:\wildfly-17.0.1.Final`).
2. Build & deploy: `mvn clean package`
3. Start WildFly: run `C:\wildfly-17.0.1.Final\bin\standalone.bat`, then open `http://localhost:8080/to_do_list/`
