# ☁️ Cloud Server Health Checker

A Java Servlet-based web application that simulates cloud server health monitoring using CPU usage and server status, classifies the server as **HEALTHY**, **WARNING**, or **CRITICAL**, and stores every health-check result in MySQL using JDBC.

---

## 🚀 Project Overview

The **Cloud Server Health Checker** is a simple cloud-monitoring simulation project built using Java, Jakarta Servlets, Apache Tomcat, JDBC, MySQL, HTML, and CSS.

The application allows a user to enter:

- Server Name
- CPU Usage
- Server Status

Based on these values, the application evaluates the server health and displays a result.

Each health-check result is also stored in MySQL and can be viewed later in the **Server Health History** page.

---

## ✨ Features

- Check simulated cloud server health
- CPU usage-based health evaluation
- Server running/stopped status validation
- HEALTHY / WARNING / CRITICAL classification
- Dynamic result page
- Persistent MySQL storage
- JDBC-based database connectivity
- Health-check history page
- Responsive UI
- Apache Tomcat deployment
- Git and GitHub version control

---

## 🧠 Health Evaluation Logic

| Condition | Health |
|---|---|
| Server status is `Stopped` | 🔴 CRITICAL |
| CPU Usage > 80% | 🔴 CRITICAL |
| CPU Usage between 60% and 80% | 🟡 WARNING |
| CPU Usage < 60% | 🟢 HEALTHY |

### Example

```text
Server Name : ec2-instance-01
CPU Usage   : 89%
Status      : Running

Result      : CRITICAL
Reason      : CPU usage is above 80%.
