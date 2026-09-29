# To-Do List Application

A Java EE 8 task management web application. Create multiple task lists, add items, mark them complete, update list names, and delete lists — all persisted in MySQL via JPA/Hibernate.

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

- **JDK 11** (tested; JDK 21 also works with `--add-opens` flags — see [Troubleshooting](#troubleshooting))
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

## Project Structure

```
src/main/java/
  controller/TodoServlet.java        — Main servlet (all GET/POST)
  dto/UserDTO.java                   — DTO (currently unused)
  entity/
    User.java                        — JPA entity → users table
    TaskList.java                    — JPA entity → task_lists table
    TaskItem.java                    — JPA entity → task_items table
  repository/
    UserRepository.java              — User CRUD
    TaskListRepository.java          — TaskList CRUD
    ItemRepository.java              — TaskItem CRUD
  rest/
    RestApplicationConfig.java       — JAX-RS app path /api
    TaskListResource.java            — REST endpoints
  service/
    UserService.java / UserImpl.java
    TaskListService.java / TaskListServiceImpl.java
    ItemService.java / ItemImpl.java
src/main/resources/
  META-INF/
    persistence.xml                  — JPA persistence unit (SquirrelPU)
    orm.xml                          — Default schema = TaskDB
  schema.sql                         — DDL (run once manually)
src/main/webapp/
  WEB-INF/web.xml                    — Deployment descriptor
  index.jsp                          — Single-page UI
  main.js                            — Async delete + warning flow
  style1.css                         — Stylesheet
```

## Setup & Run — Step by Step

### 1. Start MySQL

Make sure MySQL is running. Open PowerShell as Administrator and run:

```powershell
net start MySQL80
```

Verify it's running:

```powershell
mysql -u tram -p
# password: password
```

### 2. Create the Database (first time only)

```sql
CREATE DATABASE IF NOT EXISTS taskdb;

USE taskdb;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE task_lists (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    list_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE task_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    task_list_id INT NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (task_list_id) REFERENCES task_lists(id) ON DELETE CASCADE
);

-- The app is hardcoded to userId=2, so insert a test user
INSERT INTO users (id, username) VALUES (2, 'testuser');
```

> **Note:** The `updated_at` columns are required by the Java entities but were missing from the original `schema.sql`. The SQL above includes them.

### 3. Configure WildFly Datasource

> **Already done on your machine.** For reference, this is what was added to `C:\wildfly-17.0.1.Final\standalone\configuration\standalone.xml`:

```xml
<datasource jndi-name="java:/TaskDB_MySQLDS" pool-name="TaskDB_MySQLDS" enabled="true">
    <connection-url>jdbc:mysql://localhost:3306/taskdb</connection-url>
    <driver-class>com.mysql.cj.jdbc.Driver</driver-class>
    <driver>mysql</driver>
    <security>
        <user-name>tram</user-name>
        <password>password</password>
    </security>
</datasource>
```

The MySQL JDBC driver is auto-detected from the WAR (bundled via `mysql-connector-j` dependency). No manual module installation is needed.

### 4. Fix JDK 21 Compatibility (one-time)

> If using JDK 21, edit `C:\wildfly-17.0.1.Final\bin\common.bat` and add these lines after the existing `--add-exports`:

```batch
set "DEFAULT_MODULAR_JVM_OPTIONS=!DEFAULT_MODULAR_JVM_OPTIONS! --add-opens=java.base/java.lang=ALL-UNNAMED"
set "DEFAULT_MODULAR_JVM_OPTIONS=!DEFAULT_MODULAR_JVM_OPTIONS! --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
set "DEFAULT_MODULAR_JVM_OPTIONS=!DEFAULT_MODULAR_JVM_OPTIONS! --add-opens=java.base/java.io=ALL-UNNAMED"
set "DEFAULT_MODULAR_JVM_OPTIONS=!DEFAULT_MODULAR_JVM_OPTIONS! --add-opens=java.base/java.util=ALL-UNNAMED"
```

> This is already done on your machine. If you ever re-extract WildFly, re-apply this change.

### 5. Start WildFly

Open a **separate terminal** (keep it open):

```powershell
C:\wildfly-17.0.1.Final\bin\standalone.bat
```

Wait until you see:

```
WildFly Full 17.0.1.Final started in XXXXXms
```

### 6. Build & Deploy

In your project directory:

```powershell
# Clean and build — WAR auto-copies to WildFly's deploy folder
mvn clean package
```

This produces `target/to_do_list.war` and copies it to `C:\wildfly-17.0.1.Final\standalone\deployments\`. WildFly's deployment scanner detects it automatically.

> If WildFly is already running and the WAR didn't copy properly (e.g., the `.failed` marker exists):
> ```powershell
> del C:\wildfly-17.0.1.Final\standalone\deployments\to_do_list.war.failed
> mvn clean package
> ```

### 7. Access the App

Open your browser and go to:

**http://localhost:8080/to_do_list/**

### Quick Restart After Code Changes

After editing Java files:

```powershell
mvn clean package
```

WildFly hot-deploys the new WAR within seconds. No need to restart the server.

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

## IDE Debugging (IntelliJ)

Remote debug on port **8787**:

1. Start WildFly: `C:\wildfly-17.0.1.Final\bin\standalone.bat` (debug already enabled)
2. In IntelliJ, use the "WILDFLY" run configuration → **Debug**
3. Set breakpoints — they bind when WildFly loads the class

## Known Issues

- **User ID hardcoded to 2** — no login, no multi-user support
- **No authentication** — anyone with network access can modify data
- **No tests** — no unit or integration tests exist
- **`updated_at` missing from schema.sql** — the DDL above has been corrected; the original schema is out of sync with entities
- **Hibernate version in pom.xml is misleading** — the app declares Hibernate 4.0.0 but WildFly 17 actually uses its bundled Hibernate 5.3.10 at runtime
- **EAGER fetch on TaskList.taskItems** — loads all items on every query; can cause performance issues at scale
- **Hardcoded URLs in JavaScript** — `main.js` uses `http://localhost:8080/to_do_list/api/...` directly

## Troubleshooting

| Symptom | Likely Cause | Fix |
|---------|-------------|-----|
| Page not found (404) | WildFly not running | Start `standalone.bat` |
| Deployment fails with `InaccessibleObjectException` | JDK 17+ missing `--add-opens` | Apply the fix in Step 4 |
| Deployment fails with `Communications link failure` | MySQL not running | `net start MySQL80` |
| `Connection refused` for MySQL | Wrong port or credentials | Verify `standalone.xml` datasource config matches your MySQL setup |
| WAR not deploying | `.failed` marker present | Delete `to_do_list.war.failed` from deployments folder, rebuild |

## License

This project is an educational/demo application. No license specified.
