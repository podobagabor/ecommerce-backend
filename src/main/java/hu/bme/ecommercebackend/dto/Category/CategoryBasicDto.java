package hu.bme.ecommercebackend.dto.Category;

import hu.bme.ecommercebackend.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBasicDto {
    @NotNull
    private Long id;
    private String name;

    public CategoryBasicDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
