package hu.bme.ecommercebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

    private String description;

    public Brand(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }
}
