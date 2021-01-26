package pl.polsl.webexchange.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.WebExchangeProperties;
import pl.polsl.webexchange.user.User;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtCreator {

    private final WebExchangeProperties webExchangeProperties;

    public String createJwt(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + webExchangeProperties.getJwtConfig().getDurationInSeconds() * 1000))
                .sign(Algorithm.HMAC512(webExchangeProperties.getJwtConfig().getSecret()));
    }

}
