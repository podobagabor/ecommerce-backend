package hu.bme.ecommercebackend.dto.Category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryCreateDto {
    private String name;
    private List<Long> subCategoryIds;
    private Long parentCategoryId;
}
