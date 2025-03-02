package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer count;
    private String description;

    @Nullable
    private Integer discountPercentage;

    @OneToMany
    private List<Image> images;
    private Integer price;
    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;
}
