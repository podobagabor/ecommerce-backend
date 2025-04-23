package hu.bme.ecommercebackend.specification;

import hu.bme.ecommercebackend.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterBy(String name, List<Long> categoryId, Boolean discount, Integer minPrice, Integer maxPrice, List<Long> brandId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like( criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                predicates.add(root.get("category").get("id").in(categoryId));
            }
            if (discount != null && discount) {
                predicates.add(criteriaBuilder.isNotNull(root.get("discountPercentage")));
            }
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (brandId != null) {
                predicates.add(root.get("brand").get("id").in(brandId));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
