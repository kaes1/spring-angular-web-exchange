package pl.polsl.webexchange.currencyrate;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.polsl.webexchange.currency.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false)
    private Currency baseCurrency;

    @OneToOne(optional = false)
    private Currency targetCurrency;

    @Column(precision = 16, scale = 10)
    private BigDecimal rate;

    private LocalDateTime dateTime;

    public CurrencyRate(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, LocalDateTime dateTime) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.dateTime = dateTime;
    }
}
