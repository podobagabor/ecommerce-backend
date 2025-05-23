package hu.bme.ecommercebackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Brand {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Brand(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Brand(Long id, String name, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }
}
