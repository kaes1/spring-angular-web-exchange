package pl.polsl.webexchange.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.errorhandling.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public Currency getCurrency(String currencyCode) throws NotFoundException {
        return currencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(() -> new NotFoundException("Currency " + currencyCode + " not found"));
    }

    public List<Currency> getCurrenciesOtherThan(String currencyCode) {
        return currencyRepository.findAllByCurrencyCodeNotLike(currencyCode);
    }

}
