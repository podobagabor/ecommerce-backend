package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.CartElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartElementDto {
    private ProductDto productDto;
    private Integer quantity;

    public CartElementDto(CartElement cartElement) {
        this.productDto = new ProductDto(cartElement.getProduct());
        this.quantity = cartElement.getQuantity();
    }

}
