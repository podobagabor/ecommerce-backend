package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
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

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private List<String> images = new ArrayList<>();

    private Integer price;
    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;
}
