package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@EqualsAndHashCode
public class Order {
    @Id
    @GeneratedValue
    Long id;

    private LocalDateTime date;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    List<OrderItem> items = new ArrayList<OrderItem>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "street", column = @Column(name = "billing_street")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country")),
            @AttributeOverride(name = "number", column = @Column(name = "billing_number")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postalCode"))
    })
    Address billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country")),
            @AttributeOverride(name = "number", column = @Column(name = "shipping_number")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postalCode"))
    })
    Address shippingAddress;

    OrderStatus status;

    public Order(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.items = order.getItems();
        this.billingAddress = order.getBillingAddress();
        this.shippingAddress = order.getShippingAddress();
        this.status = order.getStatus();
        this.date = order.getDate();
    }

    public Order(User user, List<OrderItem> items, Address billingAddress, Address shippingAddress) {
        this.user = user;
        this.items = items;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.CREATED;
        this.date = LocalDateTime.now();
    }
}
