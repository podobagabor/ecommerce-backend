package hu.bme.ecommercebackend.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageModifyDto {
    private String url;
    private Boolean deleted;
}
