package hu.bme.ecommercebackend.specification;

import hu.bme.ecommercebackend.model.Brand;
import hu.bme.ecommercebackend.model.Order;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> filterBy(Long searchId, OrderStatus status) {
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
}
