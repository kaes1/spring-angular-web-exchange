package pl.polsl.webexchange.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CurrencyRateUpdater {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;

    @Scheduled(cron = "0 * * * * *")
    private void updateCurrencyRates() {
        LocalDateTime date = LocalDateTime.now();
        System.out.println("Updating currency rates now!" + date);
        currencyRepository.findAll().forEach(baseCurrency -> {
            ExchangeRateApiModel currencyRates = getCurrencyRatesFromApi(baseCurrency);
            currencyRates.getRates().forEach((code, rate) -> {
                currencyRepository.findByCurrencyCode(code).ifPresent(targetCurrency -> {
                            CurrencyRate currencyRate = new CurrencyRate(baseCurrency, targetCurrency, rate, date);
                            currencyRateRepository.save(currencyRate);
                });
            });
        });
    }

    private ExchangeRateApiModel getCurrencyRatesFromApi(Currency currency) {
        final String uri = "https://api.exchangeratesapi.io/latest?base=" + currency.getCurrencyCode();
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, ExchangeRateApiModel.class);
    }

}
