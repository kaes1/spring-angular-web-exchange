package pl.polsl.webexchange;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.webexchange.currency.CurrencyService;
import pl.polsl.webexchange.user.Role;
import pl.polsl.webexchange.user.User;
import pl.polsl.webexchange.user.UserRepository;

@SpringBootApplication
@EnableScheduling
public class WebExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebExchangeApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CurrencyService currencyService,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository) {
        return args -> {

            User admin = new User("admin@mail.com", "admin", passwordEncoder.encode("admin"), Role.ROLE_ADMIN);
            admin.activateAccount();
            admin = userRepository.save(admin);

            User user = new User("aaa@mail.com", "aaa", passwordEncoder.encode("aaa"), Role.ROLE_USER);
            user.activateAccount();
            user = userRepository.save(user);

            currencyService.createCurrency("PLN");
            currencyService.createCurrency("EUR");
            currencyService.createCurrency("CZK");
        };
    }

}
