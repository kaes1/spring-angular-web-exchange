package pl.polsl.webexchange.currencyrate;

import lombok.Value;

import java.util.List;

@Value
public class CurrencyRatesHistoryDTO {
    String targetCurrencyCode;
    List<CurrencyRateDTO> currencyRates;
}
