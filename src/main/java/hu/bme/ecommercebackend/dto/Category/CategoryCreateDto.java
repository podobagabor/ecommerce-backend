package hu.bme.ecommercebackend.dto.Category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryCreateDto {

    @NotNull
    private String name;

    private Long parentCategoryId;
}
