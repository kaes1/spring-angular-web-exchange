package pl.polsl.webexchange.register;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class RegisterRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Provided email is not valid")
    String email;

    @NotBlank(message = "Username cannot be empty")
    @Length(min = 3, message = "Username must be at least 3 characters long")
    String username;

    @NotBlank(message = "Password cannot be empty")
    @Length(min = 3, message = "Password must be at least 3 characters long")
    String password;
}
