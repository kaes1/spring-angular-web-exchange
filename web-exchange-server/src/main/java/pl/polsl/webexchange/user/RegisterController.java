package pl.polsl.webexchange.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.errorhandling.NotUniqueException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("api/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterRequest registrationRequest) {

        String email = registrationRequest.getEmail().toLowerCase();
        String username = registrationRequest.getUsername().toLowerCase();

        if (userRepository.findByEmailIgnoreCase(email).isPresent())
            throw new NotUniqueException("Email already taken");

        if (userRepository.findByUsernameIgnoreCase(username).isPresent())
            throw new NotUniqueException("Username already taken");

        User user = new User(email, username, passwordEncoder.encode(registrationRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
