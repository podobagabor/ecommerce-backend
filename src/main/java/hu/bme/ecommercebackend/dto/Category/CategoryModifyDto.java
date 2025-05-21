package hu.bme.ecommercebackend.dto.Category;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModifyDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Nullable
    private Long parentCategoryId;
}
