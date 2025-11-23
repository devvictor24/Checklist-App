# Checklist-App

This project is a Checklist application developed using Spring Boot. It provides a RESTful API for managing categories and tasks.

## Technologies Used

*   **Spring Boot**: Framework for building stand-alone, production-grade Spring-based Applications.
*   **Spring Data JPA**: For data access and persistence with Hibernate.
*   **H2 Database**: An in-memory database used for development and testing. (Could be replaced with other databases like PostgreSQL, MySQL, etc.)
*   **Maven**: Dependency management and build automation tool.
*   **Java 17**: Programming language version.

## Project Structure

The project follows a standard Spring Boot application structure:

*   `syeknom.Checklist.controller`: Handles incoming HTTP requests and returns responses.
*   `syeknom.Checklist.service`: Contains the business logic of the application.
*   `syeknom.Checklist.repository`: Provides interfaces for data access operations.
*   `syeknom.Checklist.model`: Defines the data models (entities).
*   `syeknom.Checklist.dto`: Data Transfer Objects for request and response bodies.
*   `syeknom.Checklist.exception`: Global exception handling.

## Setup and Installation

1.  **Clone the repository:**

    ```bash
    git clone <repository-url>
    cd Checklist-App-master
    ```

2.  **Navigate to the application directory:**

    ```bash
    cd Checklist-App
    ```

3.  **Build the project using Maven:**

    ```bash
    ./mvnw clean install
    ```

## How to Run

1.  **Run the Spring Boot application:**

    ```bash
    ./mvnw spring-boot:run
    ```

2.  The application will start on port 8080 by default. You can access the API endpoints at `http://localhost:8080`.

## API Endpoints (Examples)

*   **Categories:**
    *   `GET /api/categories`: Get all categories.
    *   `POST /api/categories`: Create a new category.
    *   `GET /api/categories/{id}`: Get a category by ID.
    *   `PUT /api/categories/{id}`: Update a category.
    *   `DELETE /api/categories/{id}`: Delete a category.

*   **Tasks:**
    *   `GET /api/tasks`: Get all tasks.
    *   `POST /api/tasks`: Create a new task.
    *   `GET /api/tasks/{id}`: Get a task by ID.
    *   `PUT /api/tasks/{id}`: Update a task.
    *   `DELETE /api/tasks/{id}`: Delete a task.
    *   `PATCH /api/tasks/{id}/status`: Update the status of a task.

---