package hu.bme.ecommercebackend.dto.Order;

import lombok.Getter;

@Getter
public class OrderItemCreateDto {
    Long id;
    Long productId;
    Integer quantity;
}
