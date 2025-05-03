package hu.bme.ecommercebackend.dto.Product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {

    @NotNull
    private String name;

    @NotNull
    private Integer count;

    @NotNull
    private String description;

    @Nullable
    private Integer discountPercentage;

    @NotNull
    private Integer price;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;
}
