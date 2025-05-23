package hu.bme.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode
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
}
