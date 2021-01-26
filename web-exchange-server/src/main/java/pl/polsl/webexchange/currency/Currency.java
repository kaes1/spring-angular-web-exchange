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

    private String country;

    public Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Currency(String currencyCode, String country) {
        this.currencyCode = currencyCode;
        this.country = country;
    }
}
