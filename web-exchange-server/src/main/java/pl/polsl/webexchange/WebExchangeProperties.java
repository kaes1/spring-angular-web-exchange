package pl.polsl.webexchange;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties("webexchange")
public class WebExchangeProperties {

    private List<CurrencyConfig> initialCurrencies;
    private Boolean init;
    private UserConfig admin;
    private UserConfig user;
    private JwtConfig jwtConfig;
    private String registerConfirmationUrl;

    @Getter
    @Setter
    public static class CurrencyConfig {
        private String currencyCode;
        private String country;
    }

    @Getter
    @Setter
    public static class UserConfig {
        private String email;
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class JwtConfig {
        private Integer durationInSeconds;
        private String secret;
    }
}
