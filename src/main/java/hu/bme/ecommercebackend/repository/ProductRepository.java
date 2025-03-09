package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByCategoryId(Long categoryId);
}
