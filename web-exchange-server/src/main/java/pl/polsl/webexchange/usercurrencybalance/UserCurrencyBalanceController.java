package pl.polsl.webexchange.usercurrencybalance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.currency.Currency;
import pl.polsl.webexchange.currency.CurrencyService;
import pl.polsl.webexchange.user.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserCurrencyBalanceController {

    private final UserCurrencyBalanceService userCurrencyBalanceService;
    private final CurrencyService currencyService;

    @GetMapping("api/userBalance")
    public ResponseEntity<List<UserCurrencyBalanceDTO>> getUserCurrencyBalance(@AuthenticationPrincipal User user) {
        List<Currency> allCurrencies = currencyService.getActiveCurrencies();
        List<UserCurrencyBalanceDTO> dtos = allCurrencies.stream()
                .map(currency -> userCurrencyBalanceService.getUserCurrencyBalance(user, currency))
                .map(UserCurrencyBalanceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}
