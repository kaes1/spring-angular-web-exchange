# spring-angular-web-exchange
Projekt zaliczeniowy na OiRPOS i PAI

### Uruchomienie projektu do developmentu:
- Uruchomienie WebExchangeApplication z web-exchange-server (przez IDE lub spring-boot:run)
- Uruchomienie frontendu "npm start" w web-exchange-frontend

Frontend uruchomiony w tej postaci korzysta z proxy by wysyłać zapytania HTTP do localhost:8080.

### Zbudowanie projektu i uruchomienie JAR:
mvn clean install
java -jar web-exchange-server/target/web-exchange-server-0.0.1-SNAPSHOT.jar
