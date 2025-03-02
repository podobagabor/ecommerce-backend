package hu.bme.ecommercebackend.dto.Product;

import java.util.List;

public class ProductCreateDto {
    private String name;
    private Integer count;
    private String description;
    private Integer discountPercentage;
    private List<String> images;
    private Integer price;
    private Long categoryId;
    private Long brandId;
}
