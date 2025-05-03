package hu.bme.ecommercebackend.repository;

import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.specification.OrderSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandRepository extends JpaRepository<Brand,Long>, JpaSpecificationExecutor<Brand> {
}
