package hu.bme.ecommercebackend.dto.Brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class BrandCreateDto {
    private String name;
    private String description;
}
