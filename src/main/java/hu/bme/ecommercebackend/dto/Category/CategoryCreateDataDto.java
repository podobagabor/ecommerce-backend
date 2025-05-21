package hu.bme.ecommercebackend.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDataDto {
    private List<CategoryDto> parentCategories = new ArrayList<>();
    private List<CategoryDto> childCategories = new ArrayList<>();
}
