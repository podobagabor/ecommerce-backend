package hu.bme.ecommercebackend.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String country;
    private String city;
    private String street;
    private String number;
    private String postalCode;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        return (Objects.equals(this.country, ((Address) obj).getCountry()) &&
                Objects.equals(this.city, ((Address) obj).getCity()) &&
                Objects.equals(this.street, ((Address) obj).getStreet()) &&
                Objects.equals(this.number, ((Address) obj).getNumber()) &&
                Objects.equals(this.postalCode, ((Address) obj).getPostalCode()));
    }
}
