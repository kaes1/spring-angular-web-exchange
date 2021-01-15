package pl.polsl.webexchange.operation.tradecurrency;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class TradeCurrencyResponse {
    String boughtCurrencyCode;
    String soldCurrencyCode;
    BigDecimal boughtAmount;
    BigDecimal soldAmount;
    BigDecimal newBoughtBalance;
    BigDecimal newSoldBalance;
}
