package pl.polsl.webexchange.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    List<Currency> findAllByActiveTrue();
    List<Currency> findAllByActiveTrueAndCurrencyCodeNotLike(String currencyCode);
    Optional<Currency> findByActiveTrueAndCurrencyCode(String currencyCode);
    Optional<Currency> findByCurrencyCode(String currencyCode);
}
