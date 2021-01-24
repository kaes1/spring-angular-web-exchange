package pl.polsl.webexchange;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.webexchange.currency.CurrencyService;
import pl.polsl.webexchange.user.Role;
import pl.polsl.webexchange.user.User;
import pl.polsl.webexchange.user.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CurrencyService currencyService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        User admin = new User(adminEmail, adminUsername, passwordEncoder.encode(adminPassword), Role.ROLE_ADMIN);
        admin.activateAccount();
        admin = userRepository.save(admin);

        User user = new User("aaa@mail.com", "aaa", passwordEncoder.encode("aaa"), Role.ROLE_USER);
        user.activateAccount();
        user = userRepository.save(user);

        currencyService.activateCurrency("PLN");
        currencyService.activateCurrency("EUR");
        currencyService.activateCurrency("CZK");
    }
}
