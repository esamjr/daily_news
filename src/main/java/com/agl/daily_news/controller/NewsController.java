package com.agl.daily_news.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agl.daily_news.model.Category;
import com.agl.daily_news.model.News;
import com.agl.daily_news.model.Tag;
import com.agl.daily_news.repository.CategoryRepository;
import com.agl.daily_news.repository.NewsRepository;
import com.agl.daily_news.repository.TagRepository;
import com.agl.daily_news.service.news.NewsRequest;
import com.agl.daily_news.service.news.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/newest")
    public ResponseEntity<?> getAllNews() {
        try {
            List<News> newsList = newsService.getAllNews();
            return ResponseEntity.ok(newsList);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while fetching news.");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id);
        if (news != null) {
            news.incrementViewCount();
            newsRepository.save(news);
            return ResponseEntity.ok(news);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-tag/{tag}")
    public ResponseEntity<List<News>> getNewsByTag(@PathVariable String tag) {
    List<News> newsList = newsService.getNewsByTag(tag);
    return ResponseEntity.ok(newsList);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<News>> getTrendingNews() {
        List<News> trendingNews = newsService.getTrendingNews(5);
        return ResponseEntity.ok(trendingNews);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<News> createNews(
        @PathVariable Long userId,
        @RequestBody NewsRequest createNewsRequest) {
    String title = createNewsRequest.getTitle();
    String content = createNewsRequest.getContent();
    List<String> tagNames = createNewsRequest.getTags();
    Long categoryId = createNewsRequest.getCategoryId();
    Set<Tag> tags = new HashSet<>();
    for (String tagName : tagNames) {
        Tag tag = tagRepository.findByName(tagName).orElseGet(() -> {
            Tag newTag = new Tag(tagName);
            return tagRepository.save(newTag);
        });
        tags.add(tag);

        tag.setCount(tag.getCount() + 1);
        tagRepository.save(tag);
    }
    News news = new News();
    news.setTitle(title);
    news.setContent(content);
    news.setTags(tags);
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NoSuchElementException("Category Tidak ada"));
    news.setCategory(category);
    
    News createdNews = newsService.createNews(news, userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    @PutMapping("/{id}/{userId}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News news, @PathVariable Long userId) {
        News updatedNews = newsService.updateNews(id, news, userId);
        if (updatedNews != null) {
            return ResponseEntity.ok(updatedNews);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
