package pl.polsl.webexchange.currency;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String currencyCode;

    public Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
