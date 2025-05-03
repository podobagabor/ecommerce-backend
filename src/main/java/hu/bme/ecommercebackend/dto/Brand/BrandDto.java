package hu.bme.ecommercebackend.dto.Brand;

import hu.bme.ecommercebackend.model.Brand;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return (Objects.equals(this.id,((BrandDto) obj).getId()) && Objects.equals(this.name,((BrandDto) obj).getName()) && Objects.equals(this.description,((BrandDto) obj).getDescription()) && Objects.equals(this.imageUrl,((BrandDto) obj).getImageUrl()));
    }
}
