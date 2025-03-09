package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer count;
    private String description;

    @Nullable
    private Integer discountPercentage;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private List<String> images = new ArrayList<>();

    private Integer price;
    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    public Product(
            String name,
            Integer count,
            String description,
            Integer discountPercentage,
            List<String> images,
            Integer price,
            Category category,
            Brand brand
    ) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.images = images;
        this.price = price;
        this.count = count;
    }
}
