package com.agl.daily_news.repository;

import com.agl.daily_news.model.News;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT DISTINCT n FROM News n LEFT JOIN FETCH n.comments WHERE n.id = :newsId")
    Optional<News> findByIdWithComments(@Param("newsId") Long newsId);
    @Query("SELECT n FROM News n ORDER BY n.viewCount DESC")
    List<News> findTopTrendingNews(int limit);
    List<News> findByTagsName(String tag);
    @Query("SELECT n FROM News n WHERE n.isActive = true ORDER BY n.publicationDate DESC")
    List<News> findNewestNews();

}
