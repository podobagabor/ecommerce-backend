package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.model.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderCreateDto {

    @NotNull
    Address billingAddress;

    @NotNull
    Address shippingAddress;
}
