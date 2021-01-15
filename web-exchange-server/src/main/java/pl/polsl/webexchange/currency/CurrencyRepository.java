package pl.polsl.webexchange.currency;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    List<Currency> findAllByCurrencyCodeNotLike(String currencyCode);
    Optional<Currency> findByCurrencyCode(String currencyCode);
}
