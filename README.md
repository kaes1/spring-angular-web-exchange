# spring-angular-web-exchange
Projekt zaliczeniowy na OiRPOS i PAI.

# Running the application

### Run database

PostgreSQL and pgAdmin are available via docker. To start them run `docker-compose up` in the root project folder.

### Option 1 - Running application for development:
- Change properties in `application.yml`  
  registerConfirmationUrl: "http://localhost:4200/register-confirmation/"
 
- Run WebExchangeApplication from web-exchange-server (using IDE or spring-boot:run).
- Install frontend packages by running `npm install` inside web-exchange-frontend directory.
- Run frontend by running `npm start` inside web-exchange-frontend directory.

Frontend running in this form uses a proxy to send HTTP requests to the server.  
Browser application is available on `localhost:4200`.  
Backend server is available on `localhost:8080`.

### Option 2 - Building and running application for deployment:
- Build frontend and backend by running `mvn clean install `  
- Run the resulting jar file via `java -jar web-exchange-server/target/web-exchange-server-1.0.0.jar`

Application is available on `localhost:8080`.
