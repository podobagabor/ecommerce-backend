package hu.bme.ecommercebackend.dto.Brand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class BrandCreateDto {
    @NotNull
    private String name;

    @NotNull
    private String description;
}
