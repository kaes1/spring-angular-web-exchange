# spring-angular-web-exchange
Projekt zaliczeniowy na OiRPOS i PAI


# Running the application

### Run database

PostgreSQL and pgAdmin are available via docker. To start them run `docker-compose up` in the root project folder.

### Running application for development:
- Uruchomienie WebExchangeApplication z web-exchange-server (przez IDE lub spring-boot:run)
- Uruchomienie frontendu "npm start" w web-exchange-frontend

Frontend uruchomiony w tej postaci korzysta z proxy by wysyłać zapytania HTTP do localhost:8080.

Application is available on `localhost:4200`.

### Build final version and run JAR file:
mvn clean install
java -jar web-exchange-server/target/web-exchange-server-0.0.1-SNAPSHOT.jar

Application is available on `localhost:8080`.
