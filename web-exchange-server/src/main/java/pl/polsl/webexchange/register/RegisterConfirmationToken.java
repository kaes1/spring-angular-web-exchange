package pl.polsl.webexchange.register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.webexchange.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegisterConfirmationToken {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    private String token;

    public RegisterConfirmationToken(User user, String token) {
        this.user = user;
        this.token = token;
    }
}
