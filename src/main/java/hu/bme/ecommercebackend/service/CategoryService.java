package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.customExceptions.EntityNotFoundException;
import hu.bme.ecommercebackend.customExceptions.IllegalActionException;
import hu.bme.ecommercebackend.dto.Category.*;
import hu.bme.ecommercebackend.model.Category;
import hu.bme.ecommercebackend.repository.CategoryRepository;
import hu.bme.ecommercebackend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public CategoryDto getCategoryDtoById(Long id) {
        return new CategoryDto(getCategoryById(id));
    }

    public CategoryDetailedDto getCategoryDetailedDtoById(Long id) {
        return new CategoryDetailedDto(getCategoryById(id));
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown category entity"));
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    public List<CategoryDetailedDto> getMainCategories() {
        List<Category> temp = categoryRepository.findAll().stream().filter(category -> category.getParentCategory() == null).toList();
        List<CategoryDetailedDto> newList = temp.stream().map(CategoryDetailedDto::new).toList();
        return newList;
    }

    @Transactional
    public CategoryDto createCategoryDto(CategoryCreateDto newCategory) {
        Category categoryEntity = createCategory(newCategory.getName(), newCategory.getParentCategoryId());
        if (categoryEntity == null) {
            throw new RuntimeException("Error occurred during Category creation.");
        }
        Category tempCategory = categoryRepository.save(categoryEntity);
        return new CategoryDto(tempCategory);
    }

    @Transactional
    protected Category createCategory(String name, Long parentCategoryId) {
        Category parentCategory = null;
        if (parentCategoryId != null) {
            parentCategory = categoryRepository.findById(parentCategoryId).orElseThrow(() -> new EntityNotFoundException("Unknown parent category id"));
        }
        if (parentCategory != null && parentCategory.getParentCategory() != null && parentCategory.getParentCategory().getParentCategory() != null) {
            return null;
        } else {
            return new Category(
                    name, new ArrayList<>(), parentCategory
            );
        }
    }

    @Transactional
    public CategoryDto modifyCategory(CategoryModifyDto categoryModifyDto) {
        Category modifiedCategory = getCategoryById(categoryModifyDto.getId());
        Long currentParentId = modifiedCategory.getParentCategory() != null ? modifiedCategory.getParentCategory().getId() : null;
        Long newParentId = categoryModifyDto.getParentCategoryId();

        if (!Objects.equals(currentParentId, newParentId)) {
            if (newParentId != null) {
                Category newParentCategory = getCategoryById(categoryModifyDto.getParentCategoryId());
                if (isParentCategoryValid(newParentCategory)) {
                    modifiedCategory.setParentCategory(newParentCategory);
                } else {
                    throw new IllegalActionException("Parent not appropriate");
                }
            } else {
                modifiedCategory.setParentCategory(null);
            }
        }
        modifiedCategory.setName(categoryModifyDto.getName());
        return new CategoryDto(modifiedCategory);
    }

    @Transactional
    public void deleteCategoryWithId(Long id) {
        boolean isInUse = this.productRepository.existsByCategoryId(id);
        if (!isInUse) {
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalActionException("Category is in use, or there is no category for the given id.");
        }
    }

    public CategoryCreateDataDto getCategoryCreateData() {
        List<Category> allCategory = this.categoryRepository.findAll();
        List<CategoryDto> parentCategories = new ArrayList<>();
        List<CategoryDto> childCategories = new ArrayList<>();
        allCategory.forEach(category -> {
            if (category.getParentCategory() == null || category.getParentCategory().getParentCategory() == null) {
                parentCategories.add(new CategoryDto(category));
            }
            if (category.getParentCategory() == null && (category.getSubCategories().isEmpty() || category.getSubCategories().stream().allMatch(category1 -> category1.getSubCategories().isEmpty()))) {
                childCategories.add(new CategoryDto(category));
            }
        });
        return new CategoryCreateDataDto(parentCategories, childCategories);
    }

    private boolean isParentCategoryValid(Category parentCategory) {
        return (parentCategory == null || (parentCategory.getParentCategory() == null) || (parentCategory.getParentCategory().getParentCategory() == null));
    }
}
