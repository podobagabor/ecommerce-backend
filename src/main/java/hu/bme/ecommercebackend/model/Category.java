package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "category")
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

    public Category() {
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return (Objects.equals(this.id, ((Category) obj).id) && Objects.equals(this.name,((Category) obj).getName()) && Objects.equals(this.subCategories,((Category) obj).getSubCategories()) && Objects.equals(this.parentCategory,((Category) obj).getParentCategory()));
    }
}
