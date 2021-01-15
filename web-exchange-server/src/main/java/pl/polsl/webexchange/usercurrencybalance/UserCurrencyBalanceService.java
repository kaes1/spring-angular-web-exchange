package pl.polsl.webexchange.usercurrencybalance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.user.User;

@Service
@RequiredArgsConstructor
public class UserCurrencyBalanceService {

    private final UserCurrencyBalanceRepository userCurrencyBalanceRepository;

    public UserCurrencyBalance getUserCurrencyBalance(User user, Currency currency) {
        return userCurrencyBalanceRepository.findByUserAndCurrency(user, currency)
                .orElse(new UserCurrencyBalance(user, currency));
    }

    public UserCurrencyBalance save(UserCurrencyBalance userCurrencyBalance) {
        return userCurrencyBalanceRepository.save(userCurrencyBalance);
    }
}
