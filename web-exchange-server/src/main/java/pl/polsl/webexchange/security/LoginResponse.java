package pl.polsl.webexchange.security;

import lombok.Value;
import pl.polsl.webexchange.user.Role;

@Value
public class LoginResponse {
    Boolean success;
    String message;
    String username;
    String token;
    String role;

    public static LoginResponse success(String username, String token, Role role) {
        return new LoginResponse(true, "Login successful", username, token, role.name());
    }
}
