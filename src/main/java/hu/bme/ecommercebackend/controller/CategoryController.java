package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Category.CategoryCreateDto;
import hu.bme.ecommercebackend.dto.Category.CategoryDto;
import hu.bme.ecommercebackend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @PostMapping(value = "/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody final CategoryCreateDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategoryDto(categoryDto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryDtoById(id));
    }

    @PutMapping(value = "/modify")
    public ResponseEntity<CategoryDto> modifyCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.modifyCategory(categoryDto));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategoryWithId(id));
    }
}
