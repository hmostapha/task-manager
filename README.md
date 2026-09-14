# Task Manager

Technical exercise for a simple task management application.

The goal was to have a working full-stack application while focusing on the main technical aspects of the exercise.

## Run the project

Everything is dockerized.

```bash
docker compose up --build
```

Once started:

* Frontend: http://localhost:4200
* Backend: http://localhost:8181
* Swagger: http://localhost:8181/swagger-ui/index.html

### Login

You can use:

* `user / user`
* `admin / admin`

## Backend

* Java / Spring Boot
* Hexagonal Architecture
* H2 in-memory database
* REST API
* Swagger / OpenAPI
* Liquibase
* Docker

I chose a hexagonal architecture to keep the business logic separated from technical concerns such as the API and persistence.

## Frontend

* Angular
* Angular Material
* Docker

The frontend provides the main screens needed to manage tasks and interact with the backend API.

## About the implementation

I had less time than initially expected, so I focused on the important parts rather than trying to implement everything.

I also made some choices and added a few examples to show how I would approach the problem in a real project.

### What I would add with more time

**Backend**

* PostgreSQL instead of H2
* Unit, integration and E2E tests
* CI/CD pipeline
* Sonar configuration

**Frontend**

* More tests
* Improve the overall design and UX
* More intuitive screens
* Better handling of user permissions (for example, not displaying the delete action to a regular user)
* Add some mock/demo data

The current version is therefore intentionally focused on the core functionality and the main technical choices.
