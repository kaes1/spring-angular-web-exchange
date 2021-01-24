package pl.polsl.webexchange.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.polsl.webexchange.currency.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CurrencyRateUpdater {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;

    public boolean isValidCurrencyCode(String currencyCode) {
        try {
            ExchangeRateApiModel currencyRatesFromApi = getCurrencyRatesFromApi(currencyCode);
            return currencyRatesFromApi.getRates().size() > 0;
        } catch (HttpClientErrorException exception) {
            return false;
        }
    }

    @Scheduled(cron = "0 * * * * *")
    private void updateCurrencyRates() {

        LocalDateTime date = LocalDateTime.now();
        System.out.println("Updating currency rates now! " + date);
        currencyRepository.findAll().forEach(baseCurrency -> {
            ExchangeRateApiModel exchangeRateApiResponse = getCurrencyRatesFromApi(baseCurrency.getCurrencyCode());
            exchangeRateApiResponse.getRates().forEach((currencyCode, rate) -> {
                currencyRepository.findByCurrencyCode(currencyCode).ifPresent(targetCurrency -> {
                    // We modify currency rates by 5% to simulate rates changing.
                    BigDecimal modifiedRate = modifyRate(rate);
                    CurrencyRate currencyRate = new CurrencyRate(baseCurrency, targetCurrency, modifiedRate, date);
                    currencyRateRepository.save(currencyRate);
                });
            });
        });
    }

    private ExchangeRateApiModel getCurrencyRatesFromApi(String currencyCode) {
        final String uri = "https://api.exchangeratesapi.io/latest?base=" + currencyCode;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, ExchangeRateApiModel.class);
    }

    private BigDecimal modifyRate(BigDecimal rate) {
        double modifier = randomDoubleInRange(0.95, 1.05);
        BigDecimal modifierDecimal = BigDecimal.valueOf(modifier);
        return rate.multiply(modifierDecimal);
    }

    private double randomDoubleInRange(double min, double max) {
        return min + (max - min) * Math.random();
    }
}
