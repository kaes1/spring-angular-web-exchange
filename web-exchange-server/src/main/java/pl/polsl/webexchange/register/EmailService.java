package pl.polsl.webexchange.register;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.WebExchangeProperties;
import pl.polsl.webexchange.user.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RegisterConfirmationTokenRepository registerConfirmationTokenRepository;
    private final WebExchangeProperties webExchangeProperties;

    public void sendRegistrationConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        RegisterConfirmationToken confirmationToken = new RegisterConfirmationToken(user, token);
        registerConfirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(user.getEmail());
        emailMessage.setFrom("webexchange.app@yahoo.com");
        emailMessage.setSubject("WebExchange Registration confirmation");
        emailMessage.setText("To confirm your account, please click the link: "
                + webExchangeProperties.getRegisterConfirmationUrl() + confirmationToken.getToken());
        mailSender.send(emailMessage);
    }
}
