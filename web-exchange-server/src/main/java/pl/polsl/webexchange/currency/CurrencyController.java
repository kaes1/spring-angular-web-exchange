package pl.polsl.webexchange.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("api/currencies")
    public List<CurrencyDTO> getActiveCurrencies() {
        return currencyService.getActiveCurrencies().stream()
                .map(CurrencyDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("api/currencies/configuration")
    public List<CurrencyConfigurationDTO> getCurrencyConfiguration() {
        return currencyService.getAllCurrencies().stream()
                .map(CurrencyConfigurationDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping("api/currencies/configuration")
    public void modifyCurrency(@RequestBody @Valid ModifyCurrencyRequest request) {
        currencyService.modifyCurrency(request.getCurrencyCode(), request.getCountry(), request.getActive());
    }
}
