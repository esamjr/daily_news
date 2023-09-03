package com.agl.daily_news.service.category;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agl.daily_news.model.Category;
import com.agl.daily_news.model.News;
import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.CategoryRepository;
import com.agl.daily_news.repository.UserRepository;
import com.agl.daily_news.service.user.UserService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
    }
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Category createCategory(Category category, Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getUserRole() == 1) {

            return categoryRepository.save(category);
                }
            }
        }
        throw new IllegalArgumentException("Anda tidak memiliki izin untuk membuat category.");
    }
    
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
