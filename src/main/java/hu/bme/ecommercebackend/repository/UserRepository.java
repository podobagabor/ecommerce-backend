package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u.savedProducts FROM User u WHERE u.id = :id")
    public Set<Product> findSavedProductsByUser_Id(String id);
    public List<CartElement> findCartById(String id);
}
