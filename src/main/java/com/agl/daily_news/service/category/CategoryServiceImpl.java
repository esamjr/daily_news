package com.agl.daily_news.service.category;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agl.daily_news.model.Category;
import com.agl.daily_news.model.News;
import com.agl.daily_news.model.Role;
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
        try {
            Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
            if (optionalCategory.isPresent()) {
                throw new IllegalArgumentException("Category Sudah Ada");
            }

            if (userId != null) {
                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    Role userRole = user.getUserRole();

                    if (userRole != null && userRole.getId() == 1) {
                        return categoryRepository.save(category);
                    }
                }
            }

            throw new IllegalArgumentException("Anda tidak memiliki izin untuk membuat category.");
        } catch (Exception e) {
            e.printStackTrace();
            
            throw e; 
        }
    }

    
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
