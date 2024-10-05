package com.personal.blog_app.service.impl;

import com.personal.blog_app.dto.CategoryDto;
import com.personal.blog_app.entity.Category;
import com.personal.blog_app.exeption.ResourceNotFoundException;
import com.personal.blog_app.repository.CategoryRepo;
import com.personal.blog_app.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.personal.blog_app.util.DtoEntityMapper.dtoToEntity;
import static com.personal.blog_app.util.DtoEntityMapper.entityToDto;

@Service
public class CategoryService implements ICategoryService {

    private static final String CATEGORY = "Category";
    private static final String ID = "id";
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepo.save(dtoToEntity(categoryDto, Category.class));
        return entityToDto(savedCategory, CategoryDto.class);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category savedCategory = categoryRepo.save(category);
        return entityToDto(savedCategory, CategoryDto.class);
    }

    public CategoryDto getCategory(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));
        return entityToDto(category, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream().map(category -> entityToDto(category, CategoryDto.class)).toList();
    }

    public CategoryDto deleteCategory(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));
        categoryRepo.deleteById(id);
        return entityToDto(category, CategoryDto.class);
    }

}
