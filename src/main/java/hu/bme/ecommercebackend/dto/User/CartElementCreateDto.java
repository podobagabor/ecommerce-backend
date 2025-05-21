package hu.bme.ecommercebackend.dto.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartElementCreateDto {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;
}
