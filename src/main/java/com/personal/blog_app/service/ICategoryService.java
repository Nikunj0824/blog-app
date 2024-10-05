package com.personal.blog_app.service;

import com.personal.blog_app.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, int id);
    CategoryDto getCategory(int id);
    List<CategoryDto> getAllCategory();
    CategoryDto deleteCategory(int id);
}
