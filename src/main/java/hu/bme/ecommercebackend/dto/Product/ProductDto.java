package hu.bme.ecommercebackend.dto.Product;

import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.model.Product;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Setter
public class ProductDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer count;

    @NotNull
    private String description;

    @Nullable
    private Integer discountPercentage;

    @NotNull
    private List<String> images;

    @NotNull
    private Integer price;

    @NotNull
    private CategoryDto category;

    @NotNull
    private BrandDto brand;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = new BrandDto(product.getBrand());
        this.description = product.getDescription();
        this.count = product.getCount();
        this.price = product.getPrice();
        this.category = new CategoryDto(product.getCategory());
        this.discountPercentage = product.getDiscountPercentage();
        this.images = product.getImages();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        return (Objects.equals(this.id, ((ProductDto) obj).getId()) &&
                Objects.equals(this.name, ((ProductDto) obj).getName()) &&
                Objects.equals(this.description, ((ProductDto) obj).getDescription()) &&
                Objects.equals(this.brand, ((ProductDto) obj).getBrand()) &&
                Objects.equals(this.count, ((ProductDto) obj).getCount()) &&
                Objects.equals(this.price, ((ProductDto) obj).getPrice()) &&
                Objects.equals(this.category, ((ProductDto) obj).getCategory()) &&
                Objects.equals(this.discountPercentage, ((ProductDto) obj).getDiscountPercentage()) &&
                Objects.equals(this.images, ((ProductDto) obj).getImages()));
    }
}

