package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(count, product.count) && Objects.equals(description, product.description) && Objects.equals(discountPercentage, product.discountPercentage) && Objects.equals(images, product.images) && Objects.equals(price, product.price) && Objects.equals(category, product.category) && Objects.equals(brand, product.brand) && Objects.equals(active, product.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, count, description, discountPercentage, images, price, category, brand, active);
    }
}
