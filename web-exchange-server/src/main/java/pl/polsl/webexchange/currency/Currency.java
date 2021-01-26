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

    private Boolean active;

    public Currency(String currencyCode) {
        this.currencyCode = currencyCode;
        this.country = null;
        this.active = false;
    }

    public Currency(String currencyCode, String country, Boolean active) {
        this.currencyCode = currencyCode;
        this.country = country;
        this.active = active;
    }
}
