package hu.bme.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CartElement {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;
    @Setter
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CartElement(
            Product product,
            Integer quantity,
            User user
    ) {
        this.product = product;
        this.quantity = quantity;
        this.user = user;
    }

    public CartElement(CartElement cartElement) {
        this.product = cartElement.getProduct();
        this.quantity = cartElement.getQuantity();
        this.id = cartElement.id;
        this.user = cartElement.getUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartElement that = (CartElement) o;
        return Objects.equals(id, that.id)
                && Objects.equals(product, that.product)
                && Objects.equals(quantity, that.quantity) && Objects.equals(user, that.user);
    }

}
