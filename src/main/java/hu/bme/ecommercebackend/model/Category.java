package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue
    @Getter

    private Long id;

    @Getter
    private String name;

    @Getter
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();

    @Getter
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Nullable
    private Category parentCategory;

    public Category(String name, List<Category> subCategories, Category parentCategory) {
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
        this.name = name;
    }

    public Category() {
    }
}
