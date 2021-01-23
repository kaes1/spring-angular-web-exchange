package pl.polsl.webexchange.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.webexchange.errorhandling.NotFoundException;
import pl.polsl.webexchange.errorhandling.NotUniqueException;
import pl.polsl.webexchange.user.Role;
import pl.polsl.webexchange.user.User;
import pl.polsl.webexchange.user.UserRepository;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RegisterConfirmationTokenRepository registerConfirmationTokenRepository;
    private final EmailService emailService;

    @PostMapping("api/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterRequest registrationRequest) {

        String email = registrationRequest.getEmail().toLowerCase();
        String username = registrationRequest.getUsername().toLowerCase();

        if (userRepository.findByEmailIgnoreCase(email).isPresent())
            throw new NotUniqueException("Email already taken");
        if (userRepository.findByUsernameIgnoreCase(email).isPresent())
            throw new NotUniqueException("Email already taken");

        if (userRepository.findByEmailIgnoreCase(username).isPresent())
            throw new NotUniqueException("Username already taken");
        if (userRepository.findByUsernameIgnoreCase(username).isPresent())
            throw new NotUniqueException("Username already taken");

        User user = new User(email, username, passwordEncoder.encode(registrationRequest.getPassword()), Role.ROLE_USER);
        user = userRepository.save(user);
        emailService.sendRegistrationConfirmationEmail(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("api/register/confirm")
    public ResponseEntity<Void> confirmRegistration(@RequestParam String token) {
        RegisterConfirmationToken registerConfirmationToken = registerConfirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token " + token + " not found"));

        User user = registerConfirmationToken.getUser();

        user.activateAccount();

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
