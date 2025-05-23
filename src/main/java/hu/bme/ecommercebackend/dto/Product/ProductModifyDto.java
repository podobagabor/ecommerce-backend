package hu.bme.ecommercebackend.dto.Product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductModifyDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer count;

    @NotNull
    private String description;

    private Integer discountPercentage;

    @NotNull
    private List<ImageModifyDto> images;

    @NotNull
    private Integer price;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;
}
