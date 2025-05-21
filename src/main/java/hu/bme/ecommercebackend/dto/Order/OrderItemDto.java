package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderItemDto {

    @NotNull
    Long id;

    @NotNull
    ProductDto product;

    @NotNull
    Integer quantity;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.product = new ProductDto(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
    }
}
