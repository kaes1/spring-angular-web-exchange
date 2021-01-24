package pl.polsl.webexchange.currencyrate;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class CurrencyRatesHistoryResponse {
    String baseCurrencyCode;
    String targetCurrencyCode;
    LocalDateTime from;
    LocalDateTime to;
    List<CurrencyRateDTO> currencyRates;
}
