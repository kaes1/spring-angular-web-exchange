package pl.polsl.webexchange.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.errorhandling.NotFoundException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CurrencyRateService {

    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyRate getLatestCurrencyRate(Currency baseCurrency, Currency targetCurrency) {
        return currencyRateRepository.findFirstByBaseCurrencyAndTargetCurrencyOrderByDateTimeDesc(baseCurrency, targetCurrency)
                .orElseThrow(() -> new NotFoundException(String.format("Currency Rate between %s and %s not found", baseCurrency.getCurrencyCode(), targetCurrency.getCurrencyCode())));
    }

    public List<CurrencyRate> getCurrencyRatesBetween(Currency baseCurrency, Currency targetCurrency, LocalDateTime from, LocalDateTime to) {
        return currencyRateRepository.findAllByBaseCurrencyAndTargetCurrencyAndDateTimeBetween(baseCurrency, targetCurrency, from, to);
    }

}
