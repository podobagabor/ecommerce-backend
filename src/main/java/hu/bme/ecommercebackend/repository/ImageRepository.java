package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
