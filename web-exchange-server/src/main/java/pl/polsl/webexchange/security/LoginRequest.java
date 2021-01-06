package pl.polsl.webexchange.security;

import lombok.Value;

@Value
public class LoginRequest {
    String login;
    String password;
}
