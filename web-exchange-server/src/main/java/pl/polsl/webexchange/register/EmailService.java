package pl.polsl.webexchange.register;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.user.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${registerConfirmationUrl}")
    private String registerConfirmationUrl;

    private final JavaMailSender mailSender;
    private final RegisterConfirmationTokenRepository registerConfirmationTokenRepository;

    public void sendRegistrationConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        RegisterConfirmationToken confirmationToken = new RegisterConfirmationToken(user, token);
        registerConfirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(user.getEmail());
        emailMessage.setSubject("WebExchange Registration confirmation");
        emailMessage.setText("To confirm your account, please click here : "
                + registerConfirmationUrl + confirmationToken.getToken());
        mailSender.send(emailMessage);
    }
}
