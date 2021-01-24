package pl.polsl.webexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebExchangeApplication.class, args);
    }
}
