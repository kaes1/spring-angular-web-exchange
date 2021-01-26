package pl.polsl.webexchange.currency;

import lombok.Value;

@Value
public class CurrencyDTO {

    String currencyCode;
    String country;

    public CurrencyDTO(Currency currency) {
        this.currencyCode = currency.getCurrencyCode();
        this.country = currency.getCountry();
    }
}
