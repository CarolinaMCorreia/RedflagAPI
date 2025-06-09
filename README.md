# RedflagAPI

## Table of Contents

- [Description](#description)  
- [Installation](#installation)  
- [Client](#client)  
- [API Documentation](#api-documentation)  
- [Database](#database)  
- [Deployment and CI/CD](#deployment-and-cicd)  
- [Testing and Code Quality](#testing-and-code-quality)  
- [Environment Variables](#environment-variables)  
- [Technologies](#technologies)  
- [Contact](#contact)

---

## Description

RedflagAPI is a REST API that serves as a registry of so-called "red flags." A "red flag" in a relationship is a warning sign or an indicator that something might be wrong. It may point to problematic behavior, attitudes, or dynamics that can lead to unhealthy or destructive patterns in a relationship.  
The API offers full CRUD operations to manage red flag information. The application is built with Spring Boot and uses a MySQL database hosted on AWS RDS. It runs on an AWS Elastic Beanstalk server and is integrated with AWS CodeBuild and CodePipeline for CI/CD.

---

## Installation

To run the application, follow these steps:

1. Clone this repo:
   ```bash
   git clone https://github.com/CarolinaMCorreia/RedflagAPI.git
````

2. Build and run the application using Maven:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

The application will now run connected to the database at:
`http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com`

### Deploying to AWS Elastic Beanstalk

This application is configured to deploy automatically to AWS Elastic Beanstalk on push events via a CI/CD pipeline using GitHub Actions, AWS CodeBuild, and AWS CodePipeline.

---

## Client

The API includes a simple Java-based console application that allows users to interact with the API directly from the command line.
The client is a class named `ApiClient` located in the `client` package. The class is divided into two main sections: user management and red flag management, both with full CRUD functionality.

To run the client, ensure the API application is running, then launch the client via the `main` method at the bottom of the class.

---

## API Documentation

The API is documented with Swagger. When the application is running, you can visit Swagger UI to explore and test the API.

### Accessing Swagger UI

* **Local**: `http://localhost:5000/swagger-ui/index.html`
* **AWS Elastic Beanstalk**: `http://redflags-env.eba-phvwsvmq.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html`

There is also a `generated-requests.http` file in the root of the project that provides better syntax visibility for Swagger JSON bodies.

### API Endpoints

#### Redflag Endpoints

* **GET /redflags**: Fetch all red flags.
* **POST /redflags**: Create a new red flag.

  * Request body:

    ```json
    {
      "id": 1,
      "description": "This is a description",
      "category": "BEHAVIOR",
      "examples": "Example behavior",
      "advice": "Avoid this behavior",
      "createdAt": "2023-01-01T12:00:00Z",
      "user": {
        "id": 1,
        "username": "user1"
      }
    }
    ```

#### User Endpoints

* **GET /users**: Fetch all users.
* **POST /users**: Create a new user.

  * Request body:

    ```json
    {
      "username": "john_doe",
      "password": "password123"
    }
    ```
* **PUT /users/{id}**: Update a user by ID.
* **DELETE /users/{id}**: Delete a user by ID.

---

## Javadocs

The code is documented with Javadocs. To generate and view the Javadocs, run the following command in the project root:

```bash
mvn javadoc:javadoc
```

### Accessing Javadocs

This will create the documentation in `target/site/apidocs/`. You can open the `index.html` file in a browser to browse the docs.

---

## Database

The application is connected to a MySQL database hosted on AWS RDS.

**Database setup:**
To run the application for the first time, create the `redflagdb` database manually in MySQL by connecting to your MySQL instance and running:

```sql
CREATE DATABASE redflagdb;
USE redflagdb;
```

Once the database is created, Hibernate will automatically generate the required tables when the application starts.

### Database Structure

The schema includes two main tables:

* **Users**: Stores user information
* **Redflags**: Stores red flag data linked to users

---

## Deployment and CI/CD

The application is built and deployed automatically through a CI/CD pipeline with the following components:

* **GitHub Actions**: Runs linting and ensures code quality. Configured to build the project and run tests on every push or pull request.
* **AWS CodeBuild**: Triggers a build process when a commit is pushed to the main branch, compiling the project and running tests.
* **AWS CodePipeline**: Manages the deployment process. After a successful build, the application is deployed automatically to AWS Elastic Beanstalk.

---

## Testing and Code Quality

The project includes tests and tools to maintain high code quality:

* **Service Class Testing with JUnit**: All service classes are tested with JUnit to ensure the business logic functions as expected. These tests run during the CI process to reduce bugs in production.

  * To run tests:

    ```bash
    mvn test
    ```
* **Javadocs**: All code is documented with Javadocs. Checks are included to ensure documentation completeness and accuracy.
* **Code Quality**: Linting checks are part of the build process to maintain a high standard. This includes style and Javadoc validation.

---

## Environment Variables

Application environment variables can be found in the `application.properties` file.

---

## Technologies

This application uses the following technologies and tools:

* **Spring Boot**: Backend framework
* **MySQL**: Relational database (hosted on AWS RDS)
* **Swagger**: API documentation
* **AWS Elastic Beanstalk**: Hosting and deployment
* **AWS CodeBuild & CodePipeline**: CI/CD pipeline
* **GitHub Actions**: CI and code quality checks
* **JUnit**: Unit testing for service classes
* **Javadocs**: Code documentation

---

## Contact

If you have questions or want to contribute, contact me at:
📧 [94carcon@gafe.molndal.se](mailto:94carcon@gafe.molndal.se)
or open an issue on GitHub.

---

```

Let me know if you want this file zipped or added with badges (like build status, license, etc)!
```
