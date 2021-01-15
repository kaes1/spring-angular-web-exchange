package pl.polsl.webexchange.currencyrate;

import lombok.Value;

import java.util.List;

@Value
public class LatestCurrencyRatesResponse {
    String baseCurrencyCode;
    List<CurrencyRateDTO> currencyRates;
}
