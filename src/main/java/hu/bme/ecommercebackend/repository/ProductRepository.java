package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
