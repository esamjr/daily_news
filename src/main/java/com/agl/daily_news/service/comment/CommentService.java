package com.agl.daily_news.service.comment;

import java.util.List;

import com.agl.daily_news.model.Comment;

public interface CommentService {
    Comment addComment(Comment comment, Long newsId, Long userId);
    void deleteComment(Long commentId);
}

