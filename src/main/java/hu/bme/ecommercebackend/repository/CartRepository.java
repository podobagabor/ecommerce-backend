package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartElement, Long> {
    public User findUserById(Long id);
    public List<CartElement> findAllByUser(User user);
}
