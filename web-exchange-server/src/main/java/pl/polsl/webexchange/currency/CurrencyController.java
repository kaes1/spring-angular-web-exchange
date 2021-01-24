package pl.polsl.webexchange.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.polsl.webexchange.currencyrate.ExchangeRateApiService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;
    private final ExchangeRateApiService exchangeRateApiService;

    @GetMapping("api/currencies")
    public List<CurrencyDTO> getCurrencies() {
        return currencyService.getAllCurrencies().stream()
                .map(CurrencyDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("api/currencies/configuration")
    public List<CurrencyConfigurationDTO> getCurrencyConfiguration() {
        List<String> allValidCurrencyCodes = exchangeRateApiService.getAllValidCurrencyCodes();
        return allValidCurrencyCodes.stream()
                .map(code -> new CurrencyConfigurationDTO(code, currencyService.isActive(code)))
                .collect(Collectors.toList());
    }

    @PostMapping("api/currencies/configuration")
    public void activateCurrency(@RequestBody @Valid ActivateCurrencyRequest request) {
        currencyService.activateCurrency(request.getCurrencyCode());
    }
}
