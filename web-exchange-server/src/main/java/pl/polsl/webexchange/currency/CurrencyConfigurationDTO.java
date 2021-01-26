package pl.polsl.webexchange.currency;

import lombok.Value;

@Value
public class CurrencyConfigurationDTO {
    String currencyCode;
    String country;
    Boolean active;

    public CurrencyConfigurationDTO(Currency currency) {
        this.currencyCode = currency.getCurrencyCode();
        this.country = currency.getCountry();
        this.active = currency.getActive();
    }
}
