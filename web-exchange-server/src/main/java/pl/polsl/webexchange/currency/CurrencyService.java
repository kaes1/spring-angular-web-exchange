package pl.polsl.webexchange.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.currencyrate.CurrencyRateUpdater;
import pl.polsl.webexchange.currencyrate.ExchangeRateApiService;
import pl.polsl.webexchange.errorhandling.InvalidArgumentException;
import pl.polsl.webexchange.errorhandling.NotFoundException;
import pl.polsl.webexchange.errorhandling.NotUniqueException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateApiService exchangeRateApiService;
    private final CurrencyRateUpdater currencyRateUpdater;

    public void activateCurrency(String currencyCode) {
        String uppercaseCurrencyCode = currencyCode.toUpperCase();

        currencyRepository.findByCurrencyCode(uppercaseCurrencyCode).ifPresent(currency -> {
            throw new NotUniqueException("Currency " + uppercaseCurrencyCode + " already exists");
        });

        List<String> validCurrencyCodes = exchangeRateApiService.getAllValidCurrencyCodes();

        if (!validCurrencyCodes.contains(uppercaseCurrencyCode)) {
            throw new InvalidArgumentException("Currency " + uppercaseCurrencyCode + " is not a valid currency");
        }

        Currency currency = new Currency(uppercaseCurrencyCode);
        currency = currencyRepository.save(currency);
        currencyRateUpdater.updateCurrencyRates(currency);
    }

    public Currency getCurrency(String currencyCode) throws NotFoundException {
        return currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(() -> new NotFoundException("Currency " + currencyCode + " not found"));
    }

    public boolean isActive(String currencyCode) {
        return currencyRepository.findByCurrencyCode(currencyCode).isPresent();
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public List<Currency> getCurrenciesOtherThan(String currencyCode) {
        return currencyRepository.findAllByCurrencyCodeNotLike(currencyCode);
    }
}
