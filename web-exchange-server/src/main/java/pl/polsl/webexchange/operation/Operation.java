package pl.polsl.webexchange.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.polsl.webexchange.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operation {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    private LocalDateTime dateTime;

    public Operation(User user, LocalDateTime dateTime) {
        this.user = user;
        this.dateTime = dateTime;
    }

    abstract public OperationHistory toOperationHistory();

}
