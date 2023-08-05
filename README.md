# Wallet Wizard Api
This is a Spring RESTful API that provides services for a personal wallet tracker application.

## Getting Started
To get started with the application, follow these steps:

Clone the repository to your local machine.
Open the project in your IDE of choice.
Build and run the project.

## Running the Application with Maven
To run the application using Maven, navigate to the root directory of the project and run the following command:
```
mvn spring-boot:run
```
This will compile the code, package it into an executable JAR file, and run the application. You can then access the API and the administrator interface by visiting http://localhost:8080 in your web browser.

Note that you must have Maven installed on your machine in order to run this command. You can download Maven from the official website:
[https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

If you encounter any issues while running the application with Maven, try running the following command to clean the project:
```
mvn clean
```
This will remove any previously compiled files and dependencies, and should help to resolve any errors you may be experiencing.

## application.properties
Note that the file applications.properties is missing, and you'll have to add it. Here's an example with the necessary properties:
```
server.error.include-message=always
server.error.include-binding-errors=always
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${GMAIL_USERNAME}
spring.mail.password=${GMAIL_APP_PASSWORD}
spring.mail.properties.mail.smtp.ssl.trust=*
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=3000
spring.mail.properties.mail.smtp.writetimeout=5000
jwt.secret=${SECRET_KEY}
server.url=http://localhost:8080/
client.url=http://localhost:4200/
```
If you mean to use a Gmail address, you must provide a special app password which you can obtain through your Gmail account security.

## RESTful API
To check the Api documentation run the application and go to  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Technologies Used
The application was built using the following technologies:

- Spring Boot
- Spring Data JPA
- Spring Security
- Java Mail Sender
- Springdoc
- Lombok
- JJWT
- H2
