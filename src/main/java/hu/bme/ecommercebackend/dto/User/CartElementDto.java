package hu.bme.ecommercebackend.dto.User;

import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.CartElement;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CartElementDto {
    private ProductDto productDto;
    private Integer quantity;

    public CartElementDto(CartElement cartElement) {
        this.productDto = new ProductDto(cartElement.getProduct());
        this.quantity = cartElement.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartElementDto that = (CartElementDto) o;
        return Objects.equals(productDto, that.productDto) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productDto, quantity);
    }
}
