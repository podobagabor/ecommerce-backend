package hu.bme.ecommercebackend.dto.Brand;

import hu.bme.ecommercebackend.model.Brand;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
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
