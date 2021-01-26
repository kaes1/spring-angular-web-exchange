package pl.polsl.webexchange.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.polsl.webexchange.user.User;

import java.util.Date;

@Service
public class JwtCreator {

    @Value("${webexchange.jwt.durationInSeconds}")
    private Integer JWT_DURATION_IN_SECONDS;
    @Value("${webexchange.jwt.secret}")
    private String JWT_SECRET;

    public String createJwt(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_DURATION_IN_SECONDS * 1000))
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

}
