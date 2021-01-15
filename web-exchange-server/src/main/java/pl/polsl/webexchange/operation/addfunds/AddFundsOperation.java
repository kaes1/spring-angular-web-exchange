package pl.polsl.webexchange.operation.addfunds;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.operation.Operation;
import pl.polsl.webexchange.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AddFundsOperation extends Operation {

    @ManyToOne(optional = false)
    private Currency currency;
    @Column(precision = 16, scale = 10)
    private BigDecimal amount;

    public AddFundsOperation(User user, LocalDateTime dateTime,
                             Currency currency, BigDecimal amount) {
        super(user, dateTime);
        this.currency = currency;
        this.amount = amount;
    }
}
