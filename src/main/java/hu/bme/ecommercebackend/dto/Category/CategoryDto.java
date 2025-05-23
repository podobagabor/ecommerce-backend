package hu.bme.ecommercebackend.dto.Category;

import hu.bme.ecommercebackend.model.Category;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Nullable
    private Long parentCategoryId;

    private List<Long> subCategoryIds;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if (category.getParentCategory() != null) {
            this.parentCategoryId = category.getParentCategory().getId();
        } else {
            this.parentCategoryId = null;
        }
        this.subCategoryIds = category.getSubCategories().stream().map(Category::getId).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return (Objects.equals(this.id, ((CategoryDto) obj).getId()) && Objects.equals(this.name, ((CategoryDto) obj).getName()) && Objects.equals(this.subCategoryIds, ((CategoryDto) obj).getSubCategoryIds()) && Objects.equals(this.parentCategoryId, ((CategoryDto) obj).getParentCategoryId()));
    }
}
