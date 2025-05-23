package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
}
