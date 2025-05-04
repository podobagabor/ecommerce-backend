package hu.bme.ecommercebackend.specification;

import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.model.Order;
import hu.bme.ecommercebackend.model.Product;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EcommerceSpecification {
    public static Specification<Order> filterBy(Long searchId, OrderStatus status, LocalDateTime before, LocalDateTime after) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(searchId != null) {
                Expression<String> idAsString = criteriaBuilder.function(
                        "text",
                        String.class,
                        root.get("id")
                );
                predicates.add(criteriaBuilder.like( idAsString,"%" + searchId + "%"));
            }
            if(status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            }
            if(before != null) {
                predicates.add( criteriaBuilder.lessThan(root.get("date"), before));
            }
            if(after != null) {
                predicates.add( criteriaBuilder.greaterThan(root.get("date"), after));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<Brand> filterBy(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if(name != null) {
                predicates.add(criteriaBuilder.like( criteriaBuilder.lower(root.get("name")),"%" + name.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

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
