package hu.bme.ecommercebackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
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
