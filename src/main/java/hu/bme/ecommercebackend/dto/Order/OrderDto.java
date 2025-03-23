package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    Long id;
    List<OrderItemDto> items;
    Address billingAddress;
    Address shippingAddress;

    public OrderDto(Order order) {
        this.billingAddress = order.getBillingAddress();
        this.shippingAddress = order.getShippingAddress();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
