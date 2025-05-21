package hu.bme.ecommercebackend.dto.Brand;

import hu.bme.ecommercebackend.model.Brand;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class BrandSimpleDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    public BrandSimpleDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
