package pl.polsl.webexchange.usercurrencybalance;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.user.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserCurrencyBalanceController {

    private final UserCurrencyBalanceRepository userCurrencyBalanceRepository;

    @GetMapping("api/userBalance")
    public ResponseEntity<List<UserCurrencyBalanceDTO>> getUserCurrencyBalance(@AuthenticationPrincipal User user) {
        List<UserCurrencyBalance> allByUser = userCurrencyBalanceRepository.findAllByUser(user);
        List<UserCurrencyBalanceDTO> dtos = allByUser.stream().map(UserCurrencyBalanceDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}
