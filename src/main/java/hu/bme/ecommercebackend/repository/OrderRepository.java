package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Order;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findByStatusAndIdContaining(OrderStatus status, Long id, Pageable pageable);
}
