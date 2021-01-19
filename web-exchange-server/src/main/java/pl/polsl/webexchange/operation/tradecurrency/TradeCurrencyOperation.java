package pl.polsl.webexchange.operation.tradecurrency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currencyrate.CurrencyRate;
import pl.polsl.webexchange.operation.Operation;
import pl.polsl.webexchange.operation.OperationHistory;
import pl.polsl.webexchange.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TradeCurrencyOperation extends Operation {

    @ManyToOne(optional = false)
    private Currency boughtCurrency;
    @Column(precision = 16, scale = 10)
    private BigDecimal boughtAmount;
    @ManyToOne(optional = false)
    private Currency soldCurrency;
    @Column(precision = 16, scale = 10)
    private BigDecimal soldAmount;
    @ManyToOne(optional = false)
    private CurrencyRate currencyRate;

    public TradeCurrencyOperation(User user, LocalDateTime dateTime,
                                  Currency boughtCurrency, BigDecimal boughtAmount,
                                  Currency soldCurrency, BigDecimal soldAmount,
                                  CurrencyRate currencyRate) {
        super(user, dateTime);
        this.boughtCurrency = boughtCurrency;
        this.boughtAmount = boughtAmount;
        this.soldCurrency = soldCurrency;
        this.soldAmount = soldAmount;
        this.currencyRate = currencyRate;
    }

    @Override
    public OperationHistory toOperationHistory() {
        return new OperationHistory(
                this.getDateTime(),
                String.format(Locale.ROOT, "Bought %.4f %s for %.4f %s", boughtAmount, boughtCurrency.getCurrencyCode(), soldAmount, soldCurrency.getCurrencyCode())
        );
    }
}
