package pl.polsl.webexchange;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyRepository;
import pl.polsl.webexchange.currency.CurrencyService;
import pl.polsl.webexchange.currencyrate.CurrencyRateUpdater;
import pl.polsl.webexchange.currencyrate.ExchangeRateApiService;
import pl.polsl.webexchange.user.Role;
import pl.polsl.webexchange.user.User;
import pl.polsl.webexchange.user.UserRepository;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CurrencyRepository currencyRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CurrencyRateUpdater currencyRateUpdater;
    private final ExchangeRateApiService exchangeRateApiService;
    private final WebExchangeProperties webExchangeProperties;

    @Override
    public void run(ApplicationArguments args) {

        WebExchangeProperties.UserConfig initialAdmin = webExchangeProperties.getAdmin();

        Optional<User> existingAdmin = userRepository.findByUsernameIgnoreCase(initialAdmin.getUsername());
        if (existingAdmin.isEmpty()) {
            User admin = new User(initialAdmin.getEmail(), initialAdmin.getUsername(), passwordEncoder.encode(initialAdmin.getPassword()), Role.ROLE_ADMIN);
            admin.activateAccount();
            admin = userRepository.save(admin);
        }

        WebExchangeProperties.UserConfig initialUser = webExchangeProperties.getUser();

        Optional<User> existingUser = userRepository.findByUsernameIgnoreCase(initialUser.getUsername());
        if (existingUser.isEmpty()) {
            User user = new User(initialUser.getEmail(), initialUser.getUsername(), passwordEncoder.encode(initialUser.getPassword()), Role.ROLE_USER);
            user.activateAccount();
            user = userRepository.save(user);
        }

        List<WebExchangeProperties.CurrencyConfig> initialCurrencies = webExchangeProperties.getInitialCurrencies();

        exchangeRateApiService.getAllValidCurrencyCodes().forEach(currencyCode -> {
            Currency currency = new Currency(currencyCode.toUpperCase());
            if (currencyRepository.findByCurrencyCode(currencyCode.toUpperCase()).isEmpty()) {
                initialCurrencies.stream()
                        .filter(currencyConfig -> currencyConfig.getCurrencyCode().equals(currencyCode))
                        .findFirst()
                        .ifPresent(currencyConfig -> {
                            currency.setActive(true);
                            currency.setCountry(currencyConfig.getCountry());
                        });
                currencyRepository.save(currency);
            }
        });

        this.currencyRateUpdater.updateCurrencyRates();
    }

}
