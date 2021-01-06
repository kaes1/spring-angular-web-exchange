package pl.polsl.webexchange.security;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class LoginRequest {
    @NotBlank(message = "Login cannot be empty")
    String login;
    @NotBlank(message = "Password cannot be empty")
    String password;
}
