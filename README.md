# Java Full-Stack Task Scheduler

This project is a modern, full-stack task management platform built on the Java ecosystem. It demonstrates proficiency in Core Java, database integration (JDBC/MySQL), and RESTful API development using the Spring Boot framework.

## ✨ Key Features

* **Full CRUD API:** REST endpoints for creating, reading, updating, and deleting tasks.
* **Persistent Storage:** All data is stored reliably in a **MySQL** relational database.
* **Date/Time Validation:** Prevents scheduling tasks in the past and handles invalid date formats using custom exceptions.
* **Automated Scheduling:** Uses Spring's `@Scheduled` annotation to check for and notify the user (via the server console) about overdue or upcoming tasks every minute.
* **Simple Web UI:** Includes a basic HTML/CSS/JavaScript dashboard for quick demo and testing of the API endpoints.

## 🏛️ Architecture: 3-Layered Structure

The application follows a clean, layered structure, demonstrating separation of concerns:

1.  **Controller Layer (`TaskController`):** Handles incoming HTTP requests and routes them to the service layer.
2.  **Service Layer (`TaskService`):** Contains the core business logic (validation, sorting, scheduling).
3.  **Repository Layer (`TaskRepository`):** Handles all low-level database operations (JDBC/SQL queries).
4.  **Database (MySQL):** Persistent storage for `tasks` and `users`.

## 🛠️ Tech Stack & Tools

| Component | Technology | Role |
| :--- | :--- | :--- |
| **Backend** | Java 21+ | Core programming language. |
| **Framework** | **Spring Boot 3.x** | Used for rapid API development and dependency injection. |
| **Database** | **MySQL** | Relational database for persistence. |
| **Data Access** | JDBC (Java Database Connectivity) | Used within the repository for executing raw SQL queries. |
| **Build Tool** | **Maven** | Manages project dependencies and builds. |
| **Frontend** | HTML, CSS, JavaScript | Static files served by Spring Boot for the dashboard UI. |

## ⚙️ Setup and Local Run Instructions

Follow these steps to get the application running locally:

### 1. Database Setup

Ensure you have **MySQL** installed and running (default port 3306). Create the schema using the following commands:

```sql
CREATE DATABASE task_scheduler_db;
-- (Include the rest of your CREATE TABLE and INSERT statements here)
