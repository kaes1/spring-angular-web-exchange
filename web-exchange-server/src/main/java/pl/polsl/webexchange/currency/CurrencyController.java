package pl.polsl.webexchange.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("api/currencies")
    public List<CurrencyDTO> getCurrencies() {
        return currencyService.getAllCurrencies().stream()
                .map(CurrencyDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("api/currencies/{currencyCode}")
    public CurrencyDTO getCurrency(@PathVariable String currencyCode) {
        Currency currency = currencyService.getCurrency(currencyCode);
        return new CurrencyDTO(currency);
    }

    @PostMapping("api/currencies")
    public void addCurrency(@RequestBody @Valid AddCurrencyRequest request) {
        currencyService.createCurrency(request.getCurrencyCode());
    }

    public List<String> getValidCurrencies() {
        return Arrays.asList("CZK", "HUF");
    }

}
