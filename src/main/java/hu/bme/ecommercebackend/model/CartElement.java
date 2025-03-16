package hu.bme.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter

public class CartElement {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;
    @Setter
    private Integer quantity;

    @ManyToOne()
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
}
