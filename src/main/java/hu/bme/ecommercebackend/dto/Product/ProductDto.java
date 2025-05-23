package hu.bme.ecommercebackend.dto.Product;

import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.model.Product;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode
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
}

