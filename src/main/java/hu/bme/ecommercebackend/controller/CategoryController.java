package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Category.*;
import hu.bme.ecommercebackend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping(value = "/main")
    public ResponseEntity<List<CategoryDetailedDto>> getMainCategories() {
        return ResponseEntity.ok(categoryService.getMainCategories());
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @PostMapping(value = "/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody final CategoryCreateDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategoryDto(categoryDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDtoById(id));
    }

    @GetMapping(value = "/detailed/{id}")
    public ResponseEntity<CategoryDetailedDto> getCategoryDetailedById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDetailedDtoById(id));
    }

    @GetMapping(value = "/createData")
    public ResponseEntity<CategoryCreateDataDto> getCategoryCreateData() {
        return ResponseEntity.ok(categoryService.getCategoryCreateData());
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @PutMapping(value = "/modify")
    public ResponseEntity<CategoryDto> modifyCategory(@RequestBody CategoryModifyDto categoryModifyDto) {
        return ResponseEntity.ok(categoryService.modifyCategory(categoryModifyDto));
    }

    @PreAuthorize("hasRole('ecommerce_admin')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryWithId(id);
        return ResponseEntity.accepted().build();
    }
}
