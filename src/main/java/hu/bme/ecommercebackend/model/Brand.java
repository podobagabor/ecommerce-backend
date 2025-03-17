package hu.bme.ecommercebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    public Brand(Long id, String name, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return (Objects.equals(this.getId(),((Brand) obj).getId()) && Objects.equals(this.getName(),((Brand) obj).getName()) && Objects.equals(this.description,((Brand) obj).getDescription()) && Objects.equals(this.image, ((Brand) obj).getImage()));
    }
}
