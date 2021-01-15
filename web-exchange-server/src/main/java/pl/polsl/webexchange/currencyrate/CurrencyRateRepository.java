package pl.polsl.webexchange.currencyrate;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.webexchange.currency.Currency;

import java.util.Optional;

public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findFirstByBaseCurrencyAndTargetCurrencyOrderByDateTimeDesc(Currency baseCurrency, Currency targetCurrency);
}
