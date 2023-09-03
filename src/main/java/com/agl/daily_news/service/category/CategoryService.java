package com.agl.daily_news.service.category;

import java.util.List;

import com.agl.daily_news.model.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category, Long userId);
    Category getCategoryById(Long id);
}

