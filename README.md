


# Java Server Health Checker

A Java Servlet-based web application developed using **Java, Jakarta Servlet, Apache Tomcat, JDBC, MySQL, HTML, and CSS**.

The application allows users to enter server details, checks server health based on CPU usage and server status, stores the result in MySQL, and displays previous health-check records.

---

## Project Overview

The **Java Server Health Checker** is a web application created to practice Java web development using Servlets and Apache Tomcat.

The user provides:

- Server Name
- CPU Usage
- Server Status

The application processes the input and classifies the server health as:

- HEALTHY
- WARNING
- CRITICAL

Every health-check result is stored in a MySQL database using JDBC.

---

## Features

- Java Servlet request handling
- CPU usage-based health evaluation
- Server status validation
- HEALTHY / WARNING / CRITICAL classification
- MySQL database integration
- JDBC connectivity
- PreparedStatement usage
- Persistent health-check history
- Health history page
- HTML and CSS user interface
- Apache Tomcat deployment

---

## Health Check Logic

| Condition | Result |
|---|---|
| Server status is `Stopped` | CRITICAL |
| CPU Usage > 80% | CRITICAL |
| CPU Usage between 60% and 80% | WARNING |
| CPU Usage < 60% | HEALTHY |

Example:

```text
Server Name : web-server-01
CPU Usage   : 89%
Status      : Running

Health      : CRITICAL
Reason      : CPU usage is above 80%.
````

---

## Tech Stack

| Technology      | Purpose                            |
| --------------- | ---------------------------------- |
| Java            | Backend logic                      |
| Jakarta Servlet | HTTP request and response handling |
| Apache Tomcat   | Web application server             |
| JDBC            | Java and MySQL connectivity        |
| MySQL           | Database                           |
| HTML            | Page structure                     |
| CSS             | User interface styling             |
| Eclipse IDE     | Development                        |
| Git             | Version control                    |
| GitHub          | Source code hosting                |

---

## Application Flow

```text
User
  ↓
HTML Form
  ↓
HealthCheckServlet
  ↓
Health Evaluation Logic
  ↓
JDBC
  ↓
MySQL Database
  ↓
Result Page
  ↓
HistoryServlet
  ↓
Health History Page
```

---

## Screenshots

### Home Page

![Home Page](screenshots/01-home-page.png)

### Health Result

![Health Result](screenshots/02-health-result.png)

### Health History

![Health History](screenshots/03-health-history.png)

### MySQL Records

![MySQL Records](screenshots/04-mysql-table.png)

### Apache Tomcat Server

![Apache Tomcat Server](screenshots/05-tomcat-server.png)

---

## Database

Database Name:

```sql
cloud_health_db
```

Table Name:

```sql
health_checks
```

Table Structure:

```sql
CREATE TABLE health_checks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    server_name VARCHAR(100) NOT NULL,
    cpu_usage INT NOT NULL,
    server_status VARCHAR(20) NOT NULL,
    health VARCHAR(20) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## Project Structure

```text
CloudServerHealthChecker/
│
├── src/
│   └── main/
│       │
│       ├── java/
│       │   └── com/
│       │       └── nithu/
│       │           └── cloud/
│       │               ├── HealthCheckServlet.java
│       │               └── HistoryServlet.java
│       │
│       └── webapp/
│           │
│           ├── index.html
│           │
│           ├── META-INF/
│           │   └── MANIFEST.MF
│           │
│           └── WEB-INF/
│               ├── web.xml
│               └── lib/
│                   └── mysql-connector-j-26.7.0.jar
│
└── .gitignore
```

---

## How the Application Works

### 1. User Input

The user enters:

```text
Server Name
CPU Usage
Server Status
```

The form sends the values to the servlet using an HTTP POST request.

### 2. Servlet Processing

`HealthCheckServlet` receives the request and evaluates the server health.

### 3. Database Connection

Java connects to MySQL using JDBC.

```text
Java Servlet
     ↓
    JDBC
     ↓
   MySQL
```

### 4. Store Result

The servlet stores:

* Server name
* CPU usage
* Server status
* Health
* Reason
* Checked time

### 5. Display History

`HistoryServlet` retrieves records from MySQL and displays them in the browser.

---

## Running the Project

### Requirements

Install:

* Java JDK
* Eclipse IDE for Enterprise Java
* Apache Tomcat
* MySQL Server
* MySQL Connector/J

### Create Database

```sql
CREATE DATABASE cloud_health_db;

USE cloud_health_db;
```

Create the table:

```sql
CREATE TABLE health_checks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    server_name VARCHAR(100) NOT NULL,
    cpu_usage INT NOT NULL,
    server_status VARCHAR(20) NOT NULL,
    health VARCHAR(20) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Configure MySQL Connector

Place the Connector/J JAR inside:

```text
src/main/webapp/WEB-INF/lib/
```

### Configure Apache Tomcat

In Eclipse:

```text
Project
→ Properties
→ Targeted Runtimes
→ Apache Tomcat
```

Then run:

```text
Run As
→ Run on Server
→ Apache Tomcat
```

Application URL:

```text
http://localhost:8080/CloudServerHealthChecker/
```

---

## Java Concepts Practiced

This project helped me practice:

* Java classes and methods
* Conditional statements
* Exception handling
* Servlets
* HTTP GET and POST
* Request parameters
* Response generation
* JDBC
* Connection
* PreparedStatement
* ResultSet
* SQL INSERT
* SQL SELECT
* Database authentication
* Apache Tomcat deployment

---

## Key Learning Outcome

The main goal of this project was to understand how a Java web application works end-to-end:

```text
HTML / CSS
    ↓
Java Servlet
    ↓
Application Logic
    ↓
JDBC
    ↓
MySQL
    ↓
Apache Tomcat
```

This project provided hands-on experience in connecting a Java web application to a relational database and deploying it using Apache Tomcat.

---

## Author

**Nithishkumar K**

Final-year B.Tech Information Technology student.

Interested in Java backend development, Cloud, and DevOps.

GitHub: **NITHISHKUMAR-IT**

```
