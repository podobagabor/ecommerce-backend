package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Category.CategoryCreateDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    Category mockCategory1;
    Category mockCategory2;

    @BeforeEach()
    void init() {
        mockCategory1 = new Category(1L, "Ruházat");
        mockCategory2 = new Category(2L, "Pólók", new ArrayList<>(), mockCategory1);
        mockCategory1.getSubCategories().add(mockCategory2);
    }


    @Test
    void testGetAllCategories() {
        List<Category> mockCategories = Arrays.asList(mockCategory1, mockCategory2);
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<CategoryDto> categories = categoryService.getCategories();

        assertEquals(2, categories.size());
        assertEquals("Ruházat", categories.get(0).getName());
        assertEquals("Pólók", categories.get(1).getName());
        assertEquals(1L, categories.get(1).getParentCategoryId());
        assertEquals(2L, categories.get(0).getSubCategoryIds().get(0));
    }

    @Test
    void testGetCategoryDtoById() {
        when(categoryRepository.findById(mockCategory1.getId())).thenReturn(Optional.ofNullable(mockCategory1));

        CategoryDto categoryDto = categoryService.getCategoryDtoById(mockCategory1.getId());

        assertEquals(categoryDto, new CategoryDto(mockCategory1));
    }

    @Test
    void testGetCategoryById() {
        when(categoryRepository.findById(mockCategory1.getId())).thenReturn(Optional.ofNullable(mockCategory1));

        Category category = categoryService.getCategoryById(mockCategory1.getId());

        assertEquals(category, mockCategory1);
    }

    @Test
    void testGetMainCategories() {
        List<Category> mockCategories = Arrays.asList(mockCategory1, mockCategory2);
        int numberOfMainCategories = 0;
        for (Category categoryItem : mockCategories) {
            if (categoryItem.getParentCategory() == null) numberOfMainCategories += 1;
        }
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<CategoryDto> mainCategories = categoryService.getMainCategories();
        assertEquals(numberOfMainCategories, mainCategories.size());

        for (CategoryDto categoryDto : mainCategories) {
            assertNull(categoryDto.getParentCategoryId());
        }
    }

    @Test
    void testCreateCategoryDto() {
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory2);
        when(categoryRepository.findById(mockCategory1.getId())).thenReturn(Optional.ofNullable(mockCategory1));
        when(categoryRepository.findAllById(mockCategory2.getSubCategories().stream().map(Category::getId).toList())).thenReturn(new ArrayList<>());
        CategoryDto category = categoryService.createCategoryDto(new CategoryCreateDto(
                mockCategory2.getName(), mockCategory2.getSubCategories().stream().map(Category::getId).toList(), mockCategory2.getParentCategory() != null ? mockCategory2.getParentCategory().getId() : null
        ));

        assertEquals(new CategoryDto(mockCategory2), category);
    }

    @Test
    void testModifyCategory() {
        Category mockModifiedCategory = new Category(mockCategory2.getId(), mockCategory2.getName() + " - new", mockCategory2.getSubCategories(), mockCategory2.getParentCategory());
        CategoryDto mockModifiedCategoryDto = new CategoryDto(mockModifiedCategory);
        when(categoryRepository.save(mockModifiedCategory)).thenReturn(mockModifiedCategory);
        when(categoryRepository.findById(mockCategory1.getId())).thenReturn(Optional.ofNullable(mockCategory1));

        CategoryDto modifiedCategoryDto = categoryService.modifyCategory(mockModifiedCategoryDto);
        assertEquals(modifiedCategoryDto, mockModifiedCategoryDto);
    }

    @Test
    void testDeleteCategoryWithId() {
        doNothing().when(categoryRepository).deleteById(mockCategory1.getId());

        Boolean result = categoryService.deleteCategoryWithId(mockCategory1.getId());

        assertEquals(result, true);
    }
}
