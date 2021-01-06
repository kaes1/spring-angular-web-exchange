package pl.polsl.webexchange.security;

import lombok.Value;

@Value
public class LoginResponse {
    String username;
    String token;
}
