**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

Today, you will be building a small but critical component of Current's core banking enging: real-time balance calculation through [event-sourcing](https://martinfowler.com/eaaDev/EventSourcing.html).

## Schema
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host. 

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.

You may use any technologies to support the service. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

# Candidate README
## Real-time Transaction Challenge
### Author: [Sudhanshu Dalvi](https://www.linkedin.com/in/sudhanshu-dalvi)

## Overview
In this project, I leveraged the power of Spring Boot, a popular Java framework, to implement a robust and scalable solution for the given challenge. Spring Boot's opinionated approach to configuration and its embedded server capabilities enabled me to quickly bootstrap the application and focus on the core functionality. I utilized Spring's extensive ecosystem, including Spring Data JPA for seamless database integration and object-relational mapping, and Spring MVC for building a RESTful API. Additionally, I took advantage of Spring Boot's built-in support for embedded H2 database for development and testing purposes. By embracing the Spring Boot philosophy of convention over configuration, I was able to rapidly prototype and iterate on the application, resulting in a efficient and production-ready solution that adheres to industry best practices.

## Features

- **Loads**: The service allows adding money to a user's account. This operation credits the user's account and returns the updated balance.

- **Authorizations**: The service provides the functionality to conditionally remove money from a user's account. This operation debits the user's account and returns the updated balance. Even if an authorization is declined, it is saved and does not impact the balance calculation.

- **Schema Compliance**: The service accepts all requests supported by the schema, in the format described. All responses conform to the prescribed schema.

- **Endpoint Functionality**: The authorizations endpoint works as documented in the schema.

- **Testing**: The service includes integration tests to ensure the functionality works as expected.

- **In-Memory Store**: While the service can use any technologies, it is designed to work with an in-memory store for simplicity. However, it can also be configured to use a persistent store if needed.

- **Local Bootstrap**: The service can be bootstrapped locally for testing purposes.


## Design considerations

- **Event-Sourcing with Kafka**: The project uses the event-sourcing pattern for real-time balance calculation. Changes to the application state are stored as a sequence of events. These events can be replayed to recreate the application state at any point in time. Apache Kafka, a distributed streaming platform, is used to handle these events. Kafka provides a robust, scalable solution for handling real-time data feeds with a high throughput, which is crucial for the event-sourcing pattern.

- **Spring Boot**: The project uses Spring Boot, a framework that simplifies the setup and development of Spring applications. It provides defaults for code and annotation configuration to reduce the amount of manual setup.

- **In-Memory Database**: For simplicity and ease of setup, the project uses an in-memory database (H2). However, this can be easily replaced with any SQL database for a more persistent storage solution.

- **REST API**: The project exposes a REST API for handling load and authorization transactions. The API conforms to the OpenAPI 3.0 schema provided in the service.yml file.

- **Integration Testing**: The project includes integration tests to ensure the functionality works as expected. This is crucial for maintaining the quality of the code and making sure that changes do not break existing functionality.

- **Java**: The project is implemented in Java, a robust, high-level, object-oriented programming language. Java was chosen for its wide usage, strong community support, and extensive libraries and frameworks.

- **Maven**: Maven is used for managing the project's build and dependencies. It simplifies the build process and provides a uniform build system.



## Getting Started

Below are the steps to build and run the application.

### Prerequisites

- Java (JDK 17 or higher)
- Maven (Alternatively, you can use the Maven wrapper included in the project)
- Kafka
  1. Download the latest stable release of Kafka from [here](https://kafka.apache.org/downloads).
  2. Extract the downloaded file and navigate to that file.

### Build and Run

#### Main Application
1. Clone the repository locally:

    ```bash
    git clone https://github.com/codescreen/CodeScreen_lm5sexbg.git
    ```

2. Navigate to the project directory:

    ```bash
    cd CodeScreen_lm5sexbg/
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/assessment-0.0.1-SNAPSHOT.jar
    ```

#### Transaction Listener
1. Clone the repository locally:
   Transaction Listener
   ```bash
   git clone https://github.com/sdalvi10/transaction-event-listener.git
   ```

2. Navigate to the project directory:

    ```bash
    cd transaction-event-listener/
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/assessment-query-0.0.1-SNAPSHOT.jar
    ```



#### Running ZooKeeper

Apache Kafka uses ZooKeeper, so you need to first start a ZooKeeper server if you don't already have one. You can use the convenience script packaged with Kafka to get a quick-and-dirty single-node ZooKeeper instance.

Run the following command to start ZooKeeper:

**Navigate to the Kafka installation folder.**

1. Run the following command to start the zookeeper server.
   ```bash
   bin/zookeeper-server-start.sh config/zookeeper.properties
   ```
2. Then, in another terminal window, run the following command to start the kafka server.
   ```bash
   bin/kafka-server-start.sh config/zookeeper.properties
   ```

At this point, the following should have been started and running:
- The main application
- Transaction Listener
- Kafka Zookeeper
- Kafka Server

### Server Information

- **Base URL:**
    - The main application will be running on [http://localhost:8080](http://localhost:8080)
    - The Transaction Listener will be running on [http://localhost:9292](http://localhost:9292)


## API Endpoints

The `TransactionController` class exposes the following endpoints (localhost:8080):

- **GET /ping**: This endpoint is used to check the health of the service. It returns a `Ping` object with the current server time.

- **PUT /load/{messageId}**: This endpoint is used to load money into a user's account. It accepts a `Request` object in the request body and a `messageId` in the path. The `Request` object should contain the details of the load transaction. The endpoint returns a `LoadResponse` object with the details of the transaction and the updated balance.

- **PUT /authorization/{messageId}**: This endpoint is used to authorize a debit transaction from a user's account. It accepts a `Request` object in the request body and a `messageId` in the path. The `Request` object should contain the details of the authorization transaction. The endpoint returns an `AuthorizationResponse` object with the details of the transaction and the updated balance.

The `TransactionEventController` class in the Transaction Listener exposes the following endpoint (localhost:9292):
- **GET /transactions**: Fetches all amount details from kafka listener.


## Notes and Assumptions

- The project assumes that all transactions are in the same currency. If multiple currencies are to be supported, additional logic would be required to handle currency conversion.
- The project assumes that the user's balance cannot go negative. If a debit transaction is larger than the current balance, the transaction is declined.
- The project assumes that all transaction amounts are positive. Negative amounts are not supported and will result in the transaction being declined.
- The project assumes that all users exist in the system before any transactions are made. User creation is not handled within the scope of this project.

## Integration with Kafka

The `TransactionService` class in the project uses Apache Kafka for event sourcing. Event sourcing is a pattern that derives the current state of an application from the sequence of events that have happened in the system. In this project, every transaction (load or authorization) is an event that gets published to a Kafka topic.

Here is a brief overview of how Kafka is integrated into the `TransactionService`:

- When a load or authorization request is received, the service calculates the new balance and updates the user's balance in the database.
- The service then creates a `TransactionEntity` object that represents the transaction event. This object includes details such as the transaction amount, the previous balance, the new balance, and the status of the transaction.
- The `TransactionEntity` is then wrapped in an `Event` object and published to a Kafka topic.
- The Kafka topic can then be consumed by other services or components in the system that need to know about these transaction events. For example, a separate service could consume these events to maintain a real-time balance for each user, or to generate transaction reports.


## Deployment considerations
If I were to deploy this, I would host it on a platform like AWS or Heroku. These platforms provide managed services for deploying and scaling web applications and services. They also provide services for managing databases, which would be necessary for a production version of this service.

## Submitting your solution

```
                                                                                
                   @@@@@@@@@@@@@@                                               
               @@@@@@@@@@@@@@@@@@@@@                                            
             @@@@@@@@@@@@@@@@@@@@@@@@@@                                         
          @@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@        
        @@@@@@@@@@@@@@@@@@@@@      @@@@@@                        @@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@                 .@@@@@@@@@@@@@@   
   @@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@ 
 @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@@@@@@ 
    @@@@@@@@@@@@@@               @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@    
      @@@@@@@@@@                     @@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       
         @@@@                          @@@@@@@@@@@@@@@@@@@@                     
                                          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@         
                                            @@@@@@@@@@@@@@@@@@@@@@@@            
                                               @@@@@@@@@@@@@@@@@@               
                                                    @@@@@@@@                    
```
## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/38dfe912-06ec-4fe8-bb94-73e480a0d17a" target="_blank">this screen</a>.