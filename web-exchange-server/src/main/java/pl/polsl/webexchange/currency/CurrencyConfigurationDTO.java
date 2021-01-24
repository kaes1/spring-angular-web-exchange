package pl.polsl.webexchange.currency;

import lombok.Value;

@Value
public class CurrencyConfigurationDTO {
    String currencyCode;
    Boolean active;
}
