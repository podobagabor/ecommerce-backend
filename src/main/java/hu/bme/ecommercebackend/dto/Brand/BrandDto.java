package hu.bme.ecommercebackend.dto.Brand;

import hu.bme.ecommercebackend.model.Brand;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class BrandDto {
    @NotNull
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    private String description;

    @NotNull
    private String name;

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.imageUrl = brand.getImage();
        this.description = brand.getDescription();
    }
}
