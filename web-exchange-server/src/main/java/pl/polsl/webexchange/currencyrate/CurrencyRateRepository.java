package pl.polsl.webexchange.currencyrate;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.webexchange.currency.Currency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findFirstByBaseCurrencyAndTargetCurrencyOrderByDateTimeDesc(Currency baseCurrency, Currency targetCurrency);
    Optional<CurrencyRate> findFirstByBaseCurrencyCurrencyCodeAndTargetCurrencyOrderByDateTimeDesc(String baseCurrencyCode, Currency targetCurrency);
    List<CurrencyRate> findAllByBaseCurrencyCurrencyCode(String baseCurrencyCode);
    List<CurrencyRate> findAllByBaseCurrencyAndTargetCurrencyAndDateTimeBetween(Currency baseCurrency, Currency targetCurrency, LocalDateTime dateFrom, LocalDateTime dateTo);
}
