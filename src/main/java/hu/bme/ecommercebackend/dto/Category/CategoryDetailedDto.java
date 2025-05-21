package hu.bme.ecommercebackend.dto.Category;

import hu.bme.ecommercebackend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDetailedDto extends CategoryBasicDto {
    private List<CategoryBasicDto> subCategories = new ArrayList<>();

    public CategoryDetailedDto(Category category) {
        super(category);
        this.subCategories = category.getSubCategories().stream().map(CategoryDetailedDto::new).collect(Collectors.toList());
    }
}

