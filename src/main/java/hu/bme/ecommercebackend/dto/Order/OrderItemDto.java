package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderItemDto {
    Long id;
    ProductDto product;
    Integer quantity;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.product = new ProductDto(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
    }
}
