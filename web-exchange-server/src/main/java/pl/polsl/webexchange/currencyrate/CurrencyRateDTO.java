package pl.polsl.webexchange.currencyrate;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class CurrencyRateDTO {
    String baseCurrencyCode;
    String targetCurrencyCode;
    BigDecimal rate;
    LocalDateTime dateTime;

    public CurrencyRateDTO(CurrencyRate currencyRate) {
        this.baseCurrencyCode = currencyRate.getBaseCurrency().getCurrencyCode();
        this.targetCurrencyCode = currencyRate.getTargetCurrency().getCurrencyCode();
        this.rate = currencyRate.getRate();
        this.dateTime = currencyRate.getDateTime();
    }
}
