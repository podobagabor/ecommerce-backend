package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.dto.User.UserDto;
import hu.bme.ecommercebackend.model.Address;
import hu.bme.ecommercebackend.model.Order;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDto {

    @NotNull
    Long id;

    @NotNull
    List<OrderItemDto> items;

    @NotNull
    Address billingAddress;

    @Getter
    @NotNull
    private LocalDateTime date;

    @NotNull
    Address shippingAddress;

    @NotNull
    OrderStatus status;

    @NotNull
    UserDto user;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.billingAddress = order.getBillingAddress();
        this.shippingAddress = order.getShippingAddress();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.status = order.getStatus();
        this.user = new UserDto(order.getUser());
        this.date = order.getDate();
    }
}
