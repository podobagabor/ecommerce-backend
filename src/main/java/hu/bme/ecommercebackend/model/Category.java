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
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
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

    public Category(Long id, String name, List<Category> subCategories, Category parentCategory) {
        this.subCategories = subCategories;
        this.parentCategory = parentCategory;
        this.name = name;
        this.id = id;
    }

    public Category(Long id, String name) {
        this.subCategories = new ArrayList<>();
        this.parentCategory = null;
        this.name = name;
        this.id = id;
    }
}
