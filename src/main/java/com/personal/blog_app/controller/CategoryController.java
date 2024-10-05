package com.personal.blog_app.controller;

import com.personal.blog_app.dto.ApiResponseDto;
import com.personal.blog_app.dto.CategoryDto;
import com.personal.blog_app.service.impl.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.blog_app.controller.constant.ApiConstant.*;

@RestController
@RequestMapping(value = API + CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = NEW, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping(value = CATEGORY_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable int categoryId) {
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(updateCategory);
    }

    @GetMapping(value = CATEGORY_ID)
    ResponseEntity<CategoryDto> getCategory(@PathVariable int categoryId) {
        CategoryDto category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping(value = ALL)
    ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("hasRole('ADIMN')")
    @DeleteMapping(value = CATEGORY_ID)
    ResponseEntity<ApiResponseDto> deleteCategory(@PathVariable int categoryId) {
        CategoryDto category = categoryService.deleteCategory(categoryId);
        ApiResponseDto apiResponseDto = new ApiResponseDto(String.format("Category: %s deleted successfully", category.getTitle()), true);
        return ResponseEntity.ok(apiResponseDto);
    }

}
