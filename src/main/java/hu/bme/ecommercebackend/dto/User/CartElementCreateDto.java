package hu.bme.ecommercebackend.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartElementCreateDto {
    private Long productId;
    private Integer quantity;
}
