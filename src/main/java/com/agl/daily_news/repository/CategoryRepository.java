package com.agl.daily_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agl.daily_news.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

