package pl.polsl.webexchange.usercurrencybalance;

import org.springframework.data.repository.CrudRepository;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.user.User;

import java.util.List;
import java.util.Optional;

public interface UserCurrencyBalanceRepository extends CrudRepository<UserCurrencyBalance, Long> {
    List<UserCurrencyBalance> findAllByUser(User user);
    Optional<UserCurrencyBalance> findByUserAndCurrency(User user, Currency currency);
}
