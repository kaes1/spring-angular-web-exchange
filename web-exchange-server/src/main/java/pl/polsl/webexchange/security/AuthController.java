package pl.polsl.webexchange.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.errorhandling.LoginFailedException;
import pl.polsl.webexchange.user.User;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtCreator jwtCreator;

    @PostMapping("api/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getLogin(),
                    loginRequest.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            String token = jwtCreator.createJwt(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(LoginResponse.success(user.getUsername(), token, user.getRole()));
        } catch (DisabledException disabledException) {
            throw new LoginFailedException("Account is not activated. Please activate your account.");
        } catch (AuthenticationException exception) {
            throw new LoginFailedException("Invalid username or password");
        }
    }

    @GetMapping("api/auth/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@AuthenticationPrincipal User user) {
        String token = jwtCreator.createJwt(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(LoginResponse.success(user.getUsername(), token, user.getRole()));
    }
}
