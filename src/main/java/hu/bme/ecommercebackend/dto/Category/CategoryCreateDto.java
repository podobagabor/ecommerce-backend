package hu.bme.ecommercebackend.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CategoryCreateDto {
    private String name;
    private List<Long> subCategoryIds;
    private Long parentCategoryId;
}
