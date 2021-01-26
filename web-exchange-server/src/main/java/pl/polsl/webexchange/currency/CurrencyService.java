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
    private final CurrencyRateUpdater currencyRateUpdater;

    public void modifyCurrency(String currencyCode, String newCountry, Boolean newActive) {
        Currency currency = getCurrency(currencyCode);
        if (currency.getActive() && !newActive) {
            throw new InvalidArgumentException("Cannot deactivate currency");
        }
        currency.setCountry(newCountry);
        currency.setActive(newActive);
        currencyRepository.save(currency);
        if (newActive) {
            currencyRateUpdater.updateCurrencyRates();
        }
    }

    public Currency getCurrency(String currencyCode) throws NotFoundException {
        return currencyRepository.findByCurrencyCode(currencyCode.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Currency " + currencyCode + " not found"));
    }

    public Currency getActiveCurrency(String currencyCode) throws NotFoundException {
        return currencyRepository.findByActiveTrueAndCurrencyCode(currencyCode.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Currency " + currencyCode + " not found"));
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public List<Currency> getActiveCurrencies() {
        return currencyRepository.findAllByActiveTrue();
    }

    public List<Currency> getActiveCurrenciesOtherThan(String currencyCode) {
        return currencyRepository.findAllByActiveTrueAndCurrencyCodeNotLike(currencyCode);
    }
}
