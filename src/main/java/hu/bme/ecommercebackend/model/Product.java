package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer count;
    @Column(columnDefinition = "TEXT")
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

    private Boolean active;

    public Product(
            Long id,
            String name,
            Integer count,
            String description,
            Integer discountPercentage,
            List<String> images,
            Integer price,
            Category category,
            Brand brand
    ) {
        if (id != null) {
            this.id = id;
        }
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.images = images;
        this.price = price;
        this.count = count;
        this.active = true;
    }

    public Product(
            Long id,
            String name,
            Integer count,
            String description,
            Integer discountPercentage,
            List<String> images,
            Integer price,
            Category category,
            Brand brand,
            Product product
    ) {
        this.id = id != null ? id : product.getId();
        this.brand = brand != null ? brand : product.getBrand();
        this.name = name != null ? name : product.getName();
        this.category = category != null ? category : product.getCategory();
        this.description = description != null ? description : product.getDescription();
        this.discountPercentage = discountPercentage != null ? discountPercentage : product.getDiscountPercentage();
        this.images = images != null ? images : product.getImages();
        this.price = price != null ? price : product.getPrice();
        this.count = count != null ? count : product.getCount();
        this.active = true;
    }

    public Product(Product product) {
        this.id = product.getId();
        this.brand = product.getBrand();
        this.name = product.getName();
        this.count = product.getCount();
        this.price = product.getPrice();
        this.images = product.getImages();
        this.discountPercentage = product.getDiscountPercentage();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.active = product.getActive();
    }
}
