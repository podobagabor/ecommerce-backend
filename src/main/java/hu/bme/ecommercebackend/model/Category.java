package hu.bme.ecommercebackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Category {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Nullable
    private Category parentCategory;
}
