package com.agl.daily_news.controller.admin;

import com.agl.daily_news.model.Category;
import com.agl.daily_news.service.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
public class AdminController {

    private final CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/create/{userId}")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @PathVariable Long userId) {
        Category createdCategory = categoryService.createCategory(category, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
}

