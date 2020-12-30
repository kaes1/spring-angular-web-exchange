package pl.polsl.webexchange.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyRepository currencyRepository;


    @GetMapping("api/currencies")
    public Iterable<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    @GetMapping("api/currencies/{currencyCode}")
    public Currency getCurrency(@PathVariable String currencyCode) {
        Optional<Currency> optionalCurrency = currencyRepository.findByCurrencyCode(currencyCode);
        if (optionalCurrency.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "currency not found");
        } else {
            return optionalCurrency.get();
        }
    }


}
