package pl.polsl.webexchange.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.security.LoginRequest;
import pl.polsl.webexchange.security.LoginResponse;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("api/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        User user = new User(
                registrationRequest.getUsername(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword())
        );

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/secure")
    public ResponseEntity<String[]> testSecurity() {
        return ResponseEntity.ok(new String[] {"Secure response!"});
    }

    @GetMapping("api/open")
    public ResponseEntity<String[]> testOpen() {
        return ResponseEntity.ok(new String[] {"Open response!"});
    }
}
