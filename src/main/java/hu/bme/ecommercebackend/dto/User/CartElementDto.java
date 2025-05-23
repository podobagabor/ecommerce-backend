package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.CartElement;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class CartElementDto {
    @NotNull
    private Long id;

    @NotNull
    private ProductDto productDto;

    @NotNull
    private Integer quantity;

    public CartElementDto(CartElement cartElement) {
        this.id = cartElement.getId();
        this.productDto = new ProductDto(cartElement.getProduct());
        this.quantity = cartElement.getQuantity();
    }
}
