package hu.bme.ecommercebackend.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModifyDto {
    private Long id;
    private String name;
    private Long parentCategoryId;
}
