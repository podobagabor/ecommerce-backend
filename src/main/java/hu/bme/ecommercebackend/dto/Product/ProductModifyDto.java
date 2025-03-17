package hu.bme.ecommercebackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductModifyDto {
    private Long id;
    private String name;
    private Integer count;
    private String description;
    private Integer discountPercentage;
    private List<ImageModifyDto> images;
    private List<MultipartFile> newImages;
    private Integer price;
    private Long categoryId;
    private Long brandId;
}
