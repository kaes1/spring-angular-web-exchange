package pl.polsl.webexchange.operation.addfunds;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class AddFundsRequest {
    @NotBlank(message = "Currency code cannot be empty")
    String currencyCode;
    @NotNull(message = "Amount cannot be empty")
    @DecimalMin(message = "Amount must be greater than 0", value = "0.0", inclusive = false)
    BigDecimal amount;
}
