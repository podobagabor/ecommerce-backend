package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.Order;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
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
    OrderStatus status;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.billingAddress = order.getBillingAddress();
        this.shippingAddress = order.getShippingAddress();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.status = order.getStatus();
    }
}
