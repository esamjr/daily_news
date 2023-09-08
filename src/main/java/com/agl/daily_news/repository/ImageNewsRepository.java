package com.agl.daily_news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agl.daily_news.model.ImageNews;

public interface ImageNewsRepository extends JpaRepository<ImageNews, String> {

}