package hu.bme.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@EqualsAndHashCode
public class OrderItem {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Product product;

    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    public OrderItem(CartElement cartElement, Order order) {
        this.product = cartElement.getProduct();
        this.quantity = cartElement.getQuantity();
        this.order = order;
    }

    public OrderItem(Product product, Integer quantity, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }
}


