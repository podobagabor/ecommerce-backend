package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Category.CategoryCreateDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto getCategoryById(Long id) {
        Category temp = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return new CategoryDto(temp);
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public List<CategoryDto> getMainCategories() {
        List<Category> temp = categoryRepository.findAll().stream().filter(category -> category.getParentCategory() == null).toList();
        return temp.stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public CategoryDto createCategory(CategoryCreateDto newCategory) {
        Category parentCategory = null;
        if(newCategory.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(newCategory.getParentCategoryId()).orElseThrow( () -> new EntityNotFoundException("Unknown parent category id"));
        }
        List<Category> subCategories = new ArrayList<>();
        subCategories = categoryRepository.findAllById(newCategory.getSubCategoryIds());
        Category categoryEntity =categoryRepository.save(new Category(
                newCategory.getName(), subCategories,parentCategory
        ));
        return new CategoryDto(categoryEntity);
    }
}
