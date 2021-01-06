package pl.polsl.webexchange.security;

import lombok.Value;

@Value
public class LoginResponse {
    Boolean success;
    String message;
    String username;
    String token;

    public static LoginResponse failed(String errorMessage) {
        return new LoginResponse(false, errorMessage, null, null);
    }

    public static LoginResponse success(String username, String token) {
        return new LoginResponse(true, "Login successful", username, token);
    }
}
