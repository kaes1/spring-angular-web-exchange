package pl.polsl.webexchange;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyRepository;
import pl.polsl.webexchange.currencyrate.CurrencyRate;
import pl.polsl.webexchange.currencyrate.CurrencyRateRepository;
import pl.polsl.webexchange.user.User;
import pl.polsl.webexchange.user.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class WebExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebExchangeApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CurrencyRepository currencyRepository,
						   PasswordEncoder passwordEncoder,
						   UserRepository userRepository,
						   CurrencyRateRepository currencyRateRepository) {
		return args -> {

			User user = new User("aaa@mail.com", "aaa", passwordEncoder.encode("aaa"));
			user = userRepository.save(user);

			Currency pln = new Currency("PLN");
			Currency eur = new Currency("EUR");
			Currency czk = new Currency("CZK");
			pln = currencyRepository.save(pln);
			eur = currencyRepository.save(eur);
			czk = currencyRepository.save(czk);

			currencyRepository.findAll().forEach(System.out::println);

			CurrencyRate eurToPln = new CurrencyRate(eur, pln, new BigDecimal("4.5248"), LocalDateTime.now());
			CurrencyRate plnToEur = new CurrencyRate(pln, eur, new BigDecimal("0.2210042433"), LocalDateTime.now());

			currencyRateRepository.save(eurToPln);
			currencyRateRepository.save(plnToEur);

			currencyRateRepository.findAll().forEach(System.out::println);
		};
	}

}
