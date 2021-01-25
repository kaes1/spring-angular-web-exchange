package pl.polsl.webexchange.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CurrencyRateUpdater {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final ExchangeRateApiService exchangeRateApiService;

    @Scheduled(cron = "0 * * * * *")
    public void updateCurrencyRates() {
        System.out.println("Updating currency rates now! " + LocalDateTime.now());
        currencyRepository.findAll().forEach(this::updateCurrencyRates);
    }

    public void updateCurrencyRates(Currency baseCurrency) {
        LocalDateTime date = LocalDateTime.now();
        ExchangeRateApiModel exchangeRateApiResponse = exchangeRateApiService.getCurrencyRatesFromApi(baseCurrency.getCurrencyCode());
        exchangeRateApiResponse.getRates().forEach((currencyCode, rate) -> {
            currencyRepository.findByCurrencyCode(currencyCode).ifPresent(targetCurrency -> {
                // We modify currency rates by 5% to simulate rates changing.
                BigDecimal modifiedRate = modifyRate(rate);
                CurrencyRate currencyRate = new CurrencyRate(baseCurrency, targetCurrency, modifiedRate, date);
                currencyRateRepository.save(currencyRate);
            });
        });
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
