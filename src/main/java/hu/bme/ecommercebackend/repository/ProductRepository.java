package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByCategoryId(Long categoryId);

    boolean existsByCategoryIdIn(List<Long> categories);
}
