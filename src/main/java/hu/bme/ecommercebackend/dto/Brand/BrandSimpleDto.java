package hu.bme.ecommercebackend.dto.Brand;

import hu.bme.ecommercebackend.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class BrandSimpleDto {
    private Long id;
    private String name;

    public BrandSimpleDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
