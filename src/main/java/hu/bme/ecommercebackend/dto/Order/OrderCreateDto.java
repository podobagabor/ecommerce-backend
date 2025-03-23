package hu.bme.ecommercebackend.dto.Order;

import hu.bme.ecommercebackend.model.Address;
import lombok.Getter;

@Getter
public class OrderCreateDto {
    Address billingAddress;
    Address shippingAddress;
}
