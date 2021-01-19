package pl.polsl.webexchange.operation;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.polsl.webexchange.operation.addfunds.AddFundsRequest;
import pl.polsl.webexchange.operation.addfunds.AddFundsResponse;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyRequest;
import pl.polsl.webexchange.operation.tradecurrency.TradeCurrencyResponse;
import pl.polsl.webexchange.user.User;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping(value = "api/operations/history")
    public List<OperationHistory> getOperationHistory(@AuthenticationPrincipal User user) {
        return operationService.getOperationHistory(user);
    }

//    @GetMapping(value = "api/operations/history", params = {"from"})
//    public List<OperationHistory> getOperationHistoryFrom(@AuthenticationPrincipal User user,
//                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {
//        LocalDateTime to = LocalDate.now().plusDays(1).atStartOfDay();
//        return operationService.getOperationHistory(user, from, to);
//    }
//
//    @GetMapping(value = "api/operations/history", params = {"to"})
//    public List<OperationHistory> getOperationHistoryTo(@AuthenticationPrincipal User user,
//                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
//
//        LocalDateTime from = to.minusDays(10);
//        return operationService.getOperationHistory(user, from, to);
//    }

    @GetMapping(value = "api/operations/history", params = {"from", "to"})
    public List<OperationHistory> getOperationHistoryBetween(@AuthenticationPrincipal User user,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return operationService.getOperationHistory(user, from, to);
    }
}
