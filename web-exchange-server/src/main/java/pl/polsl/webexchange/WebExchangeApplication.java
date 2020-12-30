package pl.polsl.webexchange;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyRepository;

@SpringBootApplication
public class WebExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebExchangeApplication.class, args);
	}

	@Bean
	ApplicationRunner init(CurrencyRepository currencyRepository) {
		return args -> {
			Currency c1 = new Currency("PLN");
			Currency c2 = new Currency("EUR");
			Currency c3 = new Currency("CZK");

			currencyRepository.save(c1);
			currencyRepository.save(c2);
			currencyRepository.save(c3);

			currencyRepository.findAll().forEach(System.out::println);
		};
	}

}
