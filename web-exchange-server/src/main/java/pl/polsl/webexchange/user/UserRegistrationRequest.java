package pl.polsl.webexchange.user;

import lombok.Value;

@Value
public class UserRegistrationRequest {
    String username;
    String email;
    String password;
}
