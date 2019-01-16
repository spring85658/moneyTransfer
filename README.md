# Money Transfer API
## Prerequisite

- Java 11
- JAVA_HOME should be set

## Description
This project is sample API for money transfer.

There are two API including in the project:
    
1. Get Account Balance
2. Transfer money from one account to another account

A h2 in memory db will be created when start the API

Here is the schema:

```sql
 CREATE TABLE account (
     id   BIGINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(128) NOT NULL,
     balance decimal(12,2) NOT NULL,
     PRIMARY KEY (id)
 );
 
 CREATE TABLE transaction (
     id   BIGINT NOT NULL AUTO_INCREMENT,
     from_account_id BIGINT NOT NULL,
     to_account_id BIGINT NOT NULL,
     amount decimal(12,2) NOT NULL,
     create_ts timestamp NOT NULL,
     PRIMARY KEY (id)
 ); 
 
 insert into account (id, name, balance) values (1, 'Sunny', 0), (2, 'Andy', 30);
```

## How to run tests
`./mvnw clean test`
## How to run API
`./mvnw spring-boot:run`

The API base url is http://localhost:8080/

## API Specification
The Swagger API specification is generated when API start

`mvnw spring-boot:run`

http://localhost:8080/swagger-ui.html


## 3rd party libraries used

spring-boot-starter-data-rest: spring boot restful api

spring-boot-starter-hateoas: Provides some APIs to ease creating REST representations

spring-boot-starter-data-jpa: JPA Repository Support

springfox-swagger2: Generate swagger doc

h2database: In memory db



