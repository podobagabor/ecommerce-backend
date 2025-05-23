package hu.bme.ecommercebackend.dto.Brand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandCreateDto {
    @NotNull
    private String name;

    @NotNull
    private String description;
}
