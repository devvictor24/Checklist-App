  1 # Checklist-App
    2 
    3 This project is a Checklist application developed using Spring Boot. It provides a RESTful API for managing categories and tasks.
    4 
    5 ## Technologies Used
    6 
    7 *   **Spring Boot**: Framework for building stand-alone, production-grade Spring-based Applications.
    8 *   **Spring Data JPA**: For data access and persistence with Hibernate.
    9 *   **H2 Database**: An in-memory database used for development and testing. (Could be replaced with other databases like PostgreSQL, MySQL, etc.)
   10 *   **Maven**: Dependency management and build automation tool.
   11 *   **Java 17**: Programming language version.
   12 
   13 ## Project Structure
   14 
   15 The project follows a standard Spring Boot application structure:
   16 
   17 *   `syeknom.Checklist.controller`: Handles incoming HTTP requests and returns responses.
   18 *   `syeknom.Checklist.service`: Contains the business logic of the application.
   19 *   `syeknom.Checklist.repository`: Provides interfaces for data access operations.
   20 *   `syeknom.Checklist.model`: Defines the data models (entities).
   21 *   `syeknom.Checklist.dto`: Data Transfer Objects for request and response bodies.
   22 *   `syeknom.Checklist.exception`: Global exception handling.
   23 
   24 ## Setup and Installation
   25 
   26 1.  **Clone the repository:**
      git clone <repository-url>
      cd Checklist-App-master
   1 
   2 2.  **Navigate to the application directory:**
      cd Checklist-App

   1 
   2 3.  **Build the project using Maven:**
      ./mvnw clean install
   1 
   2 ## How to Run
   3 
   4 1.  **Run the Spring Boot application:**
      ./mvnw spring-boot:run

    1 
    2 2.  The application will start on port 8080 by default. You can access the API endpoints at `http://localhost:8080`.
    3 
    4 ## API Endpoints (Examples)
    5 
    6 *   **Categories:**
    7     *   `GET /api/categories`: Get all categories.
    8     *   `POST /api/categories`: Create a new category.
    9     *   `GET /api/categories/{id}`: Get a category by ID.
   10     *   `PUT /api/categories/{id}`: Update a category.
   11     *   `DELETE /api/categories/{id}`: Delete a category.
   12 
   13 *   **Tasks:**
   14     *   `GET /api/tasks`: Get all tasks.
   15     *   `POST /api/tasks`: Create a new task.
   16     *   `GET /api/tasks/{id}`: Get a task by ID.
   17     *   `PUT /api/tasks/{id}`: Update a task.
   18     *   `DELETE /api/tasks/{id}`: Delete a task.
   19     *   `PATCH /api/tasks/{id}/status`: Update the status of a task
