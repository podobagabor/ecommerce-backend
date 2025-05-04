package hu.bme.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id) && Objects.equals(product, orderItem.product) && Objects.equals(quantity, orderItem.quantity) && Objects.equals(order, orderItem.order);
    }
}


