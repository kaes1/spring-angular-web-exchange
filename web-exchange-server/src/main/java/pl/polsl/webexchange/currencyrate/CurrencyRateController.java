package pl.polsl.webexchange.currencyrate;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyService;
import pl.polsl.webexchange.errorhandling.InvalidArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CurrencyRateController {

    private final CurrencyService currencyService;
    private final CurrencyRateService currencyRateService;

    @GetMapping("api/currencyRates/history")
    public ResponseEntity<CurrencyRatesHistoryResponse> getHistoricalCurrencyRates(
            @RequestParam(defaultValue = "EUR") String baseCurrencyCode,
            @RequestParam String targetCurrencyCode,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        Currency baseCurrency = currencyService.getActiveCurrency(baseCurrencyCode);
        Currency targetCurrency = currencyService.getActiveCurrency(targetCurrencyCode);

        LocalDateTime fromDate = from != null
                ? from
                : LocalDate.now().minusDays(10).atStartOfDay();

        LocalDateTime toDate = to != null
                ? to
                : LocalDate.now().plusDays(1).atStartOfDay();

        if (toDate.isBefore(fromDate)) {
            throw new InvalidArgumentException("End date cannot be before Start date");
        }
        
        List<CurrencyRate> currencyRates = currencyRateService.getCurrencyRatesBetween(baseCurrency, targetCurrency, fromDate, toDate);
        List<CurrencyRateDTO> currencyRateDTOs = toDTOs(currencyRates);

        CurrencyRatesHistoryResponse response = new CurrencyRatesHistoryResponse(
                baseCurrency.getCurrencyCode(),
                targetCurrency.getCurrencyCode(),
                fromDate, toDate, currencyRateDTOs
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("api/currencyRates/latest")
    public ResponseEntity<LatestCurrencyRatesResponse> getLatestCurrencyRates(@RequestParam(defaultValue = "EUR") String baseCurrencyCode, @RequestParam(required = false) String targetCurrencyCode) {
        Currency baseCurrency = currencyService.getActiveCurrency(baseCurrencyCode);
        List<Currency> targetCurrencies = targetCurrencyCode != null
                ? Collections.singletonList(currencyService.getActiveCurrency(targetCurrencyCode))
                : currencyService.getActiveCurrenciesOtherThan(baseCurrencyCode);
        List<CurrencyRate> currencyRates = targetCurrencies.stream()
                .map(targetCurrency -> currencyRateService.getLatestCurrencyRate(baseCurrency, targetCurrency))
                .collect(Collectors.toList());
        List<CurrencyRateDTO> currencyRateDTOs = toDTOs(currencyRates);
        return ResponseEntity.ok(new LatestCurrencyRatesResponse(baseCurrency.getCurrencyCode(), currencyRateDTOs));
    }

    private List<CurrencyRateDTO> toDTOs(List<CurrencyRate> currencyRates) {
        return currencyRates.stream()
                .map(CurrencyRateDTO::new)
                .collect(Collectors.toList());
    }
}
