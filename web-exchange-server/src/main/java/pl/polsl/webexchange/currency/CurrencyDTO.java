package pl.polsl.webexchange.currency;

import lombok.Value;

@Value
public class CurrencyDTO {

    String currencyCode;

    public CurrencyDTO(Currency currency) {
        this.currencyCode = currency.getCurrencyCode();
    }
}
