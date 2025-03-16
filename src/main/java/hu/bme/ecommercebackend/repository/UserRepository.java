package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    public List<Product> findSavedProductsById(String id);
}
