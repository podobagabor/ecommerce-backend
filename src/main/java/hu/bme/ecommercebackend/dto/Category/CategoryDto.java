package hu.bme.ecommercebackend.dto.Category;

import hu.bme.ecommercebackend.model.Category;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryDto {

    private Long id;

    private String name;

    @Nullable
    private Long parentCategoryId;

    private List<Long> subCategoryIds;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentCategoryId = category.getParentCategory().getId();
        this.subCategoryIds = category.getSubCategories().stream().map(Category::getId).collect(Collectors.toList());
    }
}
