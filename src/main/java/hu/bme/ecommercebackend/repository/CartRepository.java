package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.CartElement;
import hu.bme.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<CartElement, Long> {
    @Query("SELECT c.user FROM CartElement c WHERE c.id =:id")
    public User findUserById(Long id);

    public List<CartElement> findAllByUser(User user);


}
