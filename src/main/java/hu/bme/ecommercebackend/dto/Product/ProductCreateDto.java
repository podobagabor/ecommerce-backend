package hu.bme.ecommercebackend.dto.Product;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ProductCreateDto {
    private String name;
    private Integer count;
    private String description;
    private Integer discountPercentage;
    private List<MultipartFile> images;
    private Integer price;
    private Long categoryId;
    private Long brandId;
}
