package pl.polsl.webexchange.usercurrencybalance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserCurrencyBalance {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @OneToOne(optional = false)
    private Currency currency;

    @Column(precision = 16, scale = 10)
    private BigDecimal amount;

    public UserCurrencyBalance(User user, Currency currency) {
        this.user = user;
        this.currency = currency;
        this.amount = BigDecimal.ZERO;
    }

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }
}
