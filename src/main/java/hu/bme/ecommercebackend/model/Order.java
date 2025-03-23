package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    Long id;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    List<OrderItem> items = new ArrayList<OrderItem>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Embedded
    Address billingAddress;

    @Embedded
    Address shippingAddress;

    OrderStatus status;

    public Order(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.items = order.getItems();
        this.billingAddress = order.getBillingAddress();
        this.shippingAddress = order.getShippingAddress();
        this.status = order.getStatus();
    }
}
