package com.agl.daily_news.service.news;

import com.agl.daily_news.model.News;
import java.util.List;

public interface NewsService {
    List<News> getAllNews();
    News getNewsById(Long id);
    News createNews(News news, Long userId);
    News updateNews(Long id, News news, Long userId);
    void deleteNews(Long id);
    News getNewsWithComments(Long id);
    List<News> getNewsByTag(String tag);
    List<News> getTrendingNews(int limit);
}
