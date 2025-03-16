package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.CartElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartElement, Long> {
    public List<CartElement> findCartElementsByUserId(String user_id);
    public String getUserIdById(Long id);
}
