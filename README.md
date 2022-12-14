# Employee Reimbursement System Backend

## Description


The employee reimbursement system allows employees to submit tickets for business expenses. These tickets can then be approved or rejected by financial managers registered with the system. This project is the backend for the system.

The application exposes a RESTFUL API that allows employees, financial managers, and administrators to interact with the system via http requests and responses.

The application was developed in Java with the Javalin web framework. The application connects to a PostgreSQL database via the Java Database Connection API. This database primarily stores users and tickets along with a few other tables that aid in the function of the application. 

## Primary Tech Stack

**Application Tier**
- [Java 8](https://www.oracle.com/java/technologies/java8.html)
  - General purpose, object oriented, fast, secure, and reliable programming language
- [Javalin 4](https://javalin.io/archive/docs/v4.6.X.html)
  - Lightweight and flexible web framework
- [Jetty](https://www.eclipse.org/jetty/documentation.php)
  - Secure and stable web server
- [JUnit 4](https://junit.org/junit4/)
  - Framework for unit testing
- [Mockito](https://site.mockito.org/)
  - Mocking framework
- [Maven](https://maven.apache.org/)
  - Dependency management 

**Persistence Tier**
- [Docker](https://docs.docker.com/)
  - Containerization for streamlining development
- [PostgreSQL 15](https://www.postgresql.org/docs/15/index.html)
  - Reliable and performant relational database


## Development setup


- Start a postgres database you want to use for testing and run `create_tables_ddl.sql` then `initial_setup_dml.sql` in `scripts` folder
- Create a `resources` directory and a `db.properties` file under `ers-javalin-app`.
- Add properties for `url`, `username`, `password`, and `salt`
- The applications entry point is `MainDriver::main`. This will serve the application via Jetty on port `8080`.



## System

- Javalin Application that exposes a RESTFUL api for interacting with the system from a client (such as a single page application frontend). This connects to a containerized PostgreSQL relational database;

- The API is a level 2 RESTFUL api (Richardson Maturity mdel). It is stateless and resources are fetched and modified via multiple URIs and verbs.

<details><summary>System Diagram</summary>

![system](./images/system.png)

</details>


## Tables

3NF
- All data atomic and has a unique identifier
- No partial dependencies
- All values identified by a single column
- No column is dependent on a column that is not the primary key


<details><summary>Entity Relationship Diagram</summary>

![schema](./images/erd.png)

</details>

## Project Requirements
### Functional Requirement Goals

- [x] An new employee or new finance manager can request registration with the system
  - [x] `POST /registrations` 
- [x] An admin user can approve or deny new registration requests and the system will register the user's information for payment processing
  - [x] `GET /registrations`, (see registrations in system)
  - [x] `POST /users/{username}` (approve)
  - [x] `DELETE /registrations/{username}` (deny)

- [x] A registered employee can authenticate with the system by providing valid credentials
  - [x] `POST /login` 
- [x] An authenticated employee can view and manage their pending reimbursement requests
  - [x] `GET /tickets/mine/filtered` (view)
  - [x] `DELETE /tickets/mine/{id}` (and manage)
- [x] An authenticated employee can view their reimbursement request history (sortable and filterable)
  - [x] `GET /tickets/mine`
  - [x] `GET /tickets/mine/filtered` 
- [x] An authenticated employee can submit a new reimbursement request
  - `POST /tickets`
- [x] An authenticated finance manager can view a list of all pending reimbursement requests
  - `GET /tickets/pending` 
- [x] An authenticated finance manager can view a history of requests that they have approved/denied
  - `GET /tickets/resolved-by-me`
- [x] An authenticated finance manager can approve/deny reimbursement requests
  - `POST /tickets/approve/{ticket_id}`
  - `POST /tickets/reject/{ticket_id}`
- [x] An admin user can deactivate user accounts, making them unable to log into the system
  - `PUT /users/deactive/{username}` 
- [x] An admin user can reset a registered user's password
  - `PUT /users/reset/{username}` 

### Non-Functional Requirements

- [x] Basic validation is enforced to ensure that invalid/improper data is not persisted
- [x] User passwords will be encrypted by the system before persisting them to the data source
- [x] Users are unable to recall reimbursement requests once they have been processed (only pending allowed)
- [x] Sensitive endpoints are protected from unauthenticated and unauthorized requesters using JWTs
- [x] Errors and exceptions are handled properly and their details are obfuscated from the user
- [x] The system conforms to RESTful architecture constraints
- [ ] The system's is tested with at least 80% line coverage in all service and utility classes
- [x] The system keeps detailed logs on info, error, and fatal events that occur

### Suggested Bonus Features
- [ ] Authenticated employees are able to upload an receipt image along with their reimbursement request
- [ ] Run your application within a Docker container
- [ ] Automate builds using Jenkins
## Milestones

- [x] Project requirements delivered
- [x] Remote repository is created and is being kept up to date
- [x] Core model classes are created
- [x] Registration/Authentication/User operations in progress
- [x] Local DB instance running
- [x] App to DB connection made
- [x] Specified tables created with proper constraints
- [x] Registration/Authentication/User operations complete
- [x] Reimbursement operations in progress
- [x] Basic persistence layer operations in progress
- [x] Testing of business logic is in progress
- [x] Registration/Authentication web endpoints are accessible and functional
- [x] Reimbursement web endpoints are accessible and functional
- [x] User passwords are encrypted when persisted to the DB

## API


<hr/>

<details>
  <summary>
    Registration and Authentication
  </summary>

<p>

**Register an account**

 ```
 POST /registration

 authorization: anyone

body
 {
    "username": "examplename",
    "passwordOne" "12345ABc",
    "passwordTwo" "12345ABc",
    "email": "fake@email.com",
    "givenName": "John",
    "surname": "Doe",
    "roleId": "EMPLOYEE" (EMPLOYEE, MANAGER, ADMIN)
 }
 
 ```

 **Approve Registration**

 ```
 PUT /registration/{username}

 authorization: administrators
 ```
 
 **Login**
 ```
 POST /login

 body
 {
    "username": "jdoe",
    "password": "securePw123",
 }

 response

 {
  token: "example-token-string-asdfghjkl123456"
 }

 ```

 </p>
 
</details>


<details>

<summary>
  Reimbursement ticket management
</summary>

 **Submit Reimbursement Ticket**
 ```
 POST /tickets
 header
 authorization: any logged in (employee, manager, or administrators)

 body
 {
    "amount": "20.00" (greater than 0)
    "description": "lunch with client" (not empty)
    "type": "food" (lodging, travel, food, other)
 }
 ```

</details>
