package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
