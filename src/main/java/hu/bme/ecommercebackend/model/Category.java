package hu.bme.ecommercebackend.model;

import hu.bme.ecommercebackend.dto.Category.CategoryCreateDto;
import jakarta.annotation.Generated;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
public class Category {
    @Id
    @GeneratedValue

    private Long id;

    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Nullable
    private Category parentCategory;

    public Category(String name, List<Category> subCategories, Category parentCategory) {
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
        this.name = name;
    }

    public Category() {}
}
