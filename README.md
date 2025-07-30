## Task Manager API
A simple RESTful API built with Spring Boot for managing tasks assigned to users. This project uses PostgreSQL as the backend database.

## Prerequisites
- Java 17+
- Maven
- PostgreSQL installed and running locally

## Getting Started

1) Clone the Repository

`git clone https://github.com/your-username/taskManager.git`

`cd taskManager`

2) Open PostgreSQL CLI or a GUI like pgAdmin.

3) Set Your Password And remember it for future use

`ALTER USER postgres WITH PASSWORD 'your_new_password';`

4) Create a database:

`CREATE DATABASE taskmanager;`

5) Ensure the user credentials match your application.properties:

`spring.datasource.username=postgres`
`spring.datasource.password=your_new_password `

6) Run the application

`mvn spring-boot:run`