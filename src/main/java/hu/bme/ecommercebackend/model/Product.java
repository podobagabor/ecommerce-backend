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
        if(id != null) {
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
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return (
                        Objects.equals(this.id, ((Product) obj).getId()) &&
                        Objects.equals(this.name, ((Product) obj).getName()) &&
                        Objects.equals(this.description, ((Product) obj).getDescription()) &&
                        Objects.equals(this.brand, ((Product) obj).getBrand()) &&
                        Objects.equals(this.category, ((Product) obj).getCategory()) &&
                        Objects.equals(this.count, ((Product) obj).getCount()) &&
                        Objects.equals(this.price, ((Product) obj).getPrice()) &&
                        Objects.equals(this.images, ((Product) obj).getImages()) &&
                        Objects.equals(this.discountPercentage, ((Product) obj).getDiscountPercentage())
        );
    }
}
