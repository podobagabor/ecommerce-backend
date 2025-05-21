package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Category.CategoryCreateDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDetailedDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.dto.Category.CategoryModifyDto;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.repository.CategoryRepository;
import hu.bme.ecommercebackend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    ProductRepository productRepository;

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

        assertEquals(mockCategories.size(), categories.size());
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categories.get(i), new CategoryDto(mockCategories.get(i)));
        }
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
        List<CategoryDetailedDto> mainCategories = categoryService.getMainCategories();
        assertEquals(numberOfMainCategories, mainCategories.size());

        for (CategoryDetailedDto categoryDetailedDto : mainCategories) {
            assertTrue(mockCategories.stream().anyMatch(category -> (Objects.equals(category.getId(), categoryDetailedDto.getId()) && category.getParentCategory() == null)));
        }
    }

    @Test
    void testCreateCategoryDto() {
        Category mockCategoryWithoutId = new Category(null, mockCategory2.getName(), mockCategory2.getSubCategories(), mockCategory2.getParentCategory());

        when(categoryRepository.save(mockCategoryWithoutId)).thenReturn(mockCategoryWithoutId);
        when(categoryRepository.findById(mockCategory1.getId())).thenReturn(Optional.ofNullable(mockCategory1));

        CategoryDto category = categoryService.createCategoryDto(new CategoryCreateDto(
                mockCategory2.getName(), mockCategory2.getParentCategory() != null ? mockCategory2.getParentCategory().getId() : null
        ));
        assertEquals(new CategoryDto(mockCategoryWithoutId), category);

    }

    @Test
    void testModifyCategory() {
        Category mockModifiedCategory = new Category(mockCategory2.getId(), mockCategory2.getName() + " - new", mockCategory2.getSubCategories(), mockCategory2.getParentCategory());

        when(categoryRepository.findById(mockCategory2.getId())).thenReturn(Optional.ofNullable(mockCategory2));
        Long parentCategoryId = mockModifiedCategory.getParentCategory() != null ? mockModifiedCategory.getParentCategory().getId() : null;
        CategoryDto modifiedCategoryDto = categoryService.modifyCategory(new CategoryModifyDto(mockModifiedCategory.getId(),mockModifiedCategory.getName(),parentCategoryId));

        assertEquals(modifiedCategoryDto, new CategoryDto(mockModifiedCategory));
    }

    @Test
    void testDeleteCategoryWithId() {
        doNothing().when(categoryRepository).deleteById(mockCategory1.getId());
        when(productRepository.existsByCategoryIdIn(Arrays.asList(mockCategory1.getId(),mockCategory2.getId()))).thenReturn(false);
        when(categoryRepository.findById(mockCategory1.getId())).thenReturn(Optional.ofNullable(mockCategory1));

        categoryService.deleteCategoryWithId(mockCategory1.getId());
    }
}
