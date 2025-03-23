package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
