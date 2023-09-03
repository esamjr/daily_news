package com.agl.daily_news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agl.daily_news.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNewsId(Long newsId);
    @Query("SELECT c.id FROM Comment c WHERE c.news.id = :newsId")
    List<Long> findCommentIdsByNewsId(@Param("newsId") Long newsId);
}

