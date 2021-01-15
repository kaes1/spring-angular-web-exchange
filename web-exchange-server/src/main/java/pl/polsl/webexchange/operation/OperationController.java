package pl.polsl.webexchange.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.operation.addfunds.AddFundsRequest;
import pl.polsl.webexchange.operation.addfunds.AddFundsResponse;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyRequest;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyResponse;
import pl.polsl.webexchange.user.User;

@RestController
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping("api/operations/addFunds")
    public ResponseEntity<AddFundsResponse> addFunds(@RequestBody AddFundsRequest addFundsRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(operationService.addFunds(user, addFundsRequest));
    }

    @PostMapping("api/operations/tradeCurrency")
    public ResponseEntity<TradeCurrencyResponse> tradeCurrency(@RequestBody TradeCurrencyRequest tradeCurrencyRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(operationService.tradeCurrency(user, tradeCurrencyRequest));
    }

    //TODO GET operations history
}
