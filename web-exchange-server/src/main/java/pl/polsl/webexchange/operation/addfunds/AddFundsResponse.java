package pl.polsl.webexchange.operation.addfunds;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class AddFundsResponse {
    String currencyCode;
    BigDecimal amount;
    BigDecimal newBalance;
}
