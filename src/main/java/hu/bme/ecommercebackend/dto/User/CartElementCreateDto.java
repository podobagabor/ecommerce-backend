package hu.bme.ecommercebackend.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartElementCreateDto {
    private Long productId;
    private Integer quantity;
}
