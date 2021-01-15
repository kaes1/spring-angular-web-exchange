package pl.polsl.webexchange.usercurrencybalance;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class UserCurrencyBalanceDTO {
    String currencyCode;
    BigDecimal amount;

    public UserCurrencyBalanceDTO(UserCurrencyBalance userCurrencyBalance) {
        this.currencyCode = userCurrencyBalance.getCurrency().getCurrencyCode();
        this.amount = userCurrencyBalance.getAmount();
    }
}
