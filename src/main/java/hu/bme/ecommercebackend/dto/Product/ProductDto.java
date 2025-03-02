package hu.bme.ecommercebackend.dto.Product;

import hu.bme.ecommercebackend.dto.Brand.BrandDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private Integer count;
    private String description;
    private Integer discountPercentage;
    private List<String> images;
    private Integer price;
    private CategoryDto category;
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

