# spring-angular-web-exchange
Projekt zaliczeniowy na OiRPOS i PAI.


# Running the application

### Run database

PostgreSQL and pgAdmin are available via docker. To start them run `docker-compose up` in the root project folder.

### Running application for development:
- Run WebExchangeApplication from web-exchange-server (using IDE or spring-boot:run)
- Run frontend via `npm start` inside web-exchange-frontend directory.

Frontend running in this form uses a proxy to send HTTP requests to the server.  
Browser application is available on `localhost:4200`.  
Backend server is available on `localhost:8080`

### Build final version and run JAR file:
mvn clean install  
java -jar web-exchange-server/target/web-exchange-server-0.0.1-SNAPSHOT.jar

Application is available on `localhost:8080`.
