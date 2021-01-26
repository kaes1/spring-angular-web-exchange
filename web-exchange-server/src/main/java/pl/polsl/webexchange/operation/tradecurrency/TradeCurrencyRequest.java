package pl.polsl.webexchange.operation.tradecurrency;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class TradeCurrencyRequest {
    @NotBlank(message = "Sold currency code cannot be empty")
    String soldCurrencyCode;
    @NotBlank(message = "Bought currency code cannot be empty")
    String boughtCurrencyCode;
    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(message = "Amount must be greater than 0", value = "0.0", inclusive = false)
    BigDecimal sellAmount;
    @NotNull(message = "Amount cannot be empty")
    BigDecimal rate;
}
