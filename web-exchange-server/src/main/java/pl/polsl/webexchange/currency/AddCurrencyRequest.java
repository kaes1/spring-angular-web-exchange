package pl.polsl.webexchange.currency;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddCurrencyRequest {
    @NotBlank
    @Size(min = 3, max = 3)
    String currencyCode;
}
