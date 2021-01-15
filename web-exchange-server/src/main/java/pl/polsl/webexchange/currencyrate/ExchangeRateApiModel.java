package pl.polsl.webexchange.currencyrate;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRateApiModel {
    private Map<String, BigDecimal> rates;
    private String base;
    private String date;
}
