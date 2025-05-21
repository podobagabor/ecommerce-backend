package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.model.Address;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderCreateDto {

    @NotNull
    Address billingAddress;

    @NotNull
    Address shippingAddress;
}
