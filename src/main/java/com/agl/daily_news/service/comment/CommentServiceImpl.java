package com.agl.daily_news.service.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agl.daily_news.model.Comment;
import com.agl.daily_news.model.News;
import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.CommentRepository;
import com.agl.daily_news.repository.NewsRepository;
import com.agl.daily_news.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment addComment(Comment comment, Long newsId, Long userId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalNews.isPresent() && optionalUser.isPresent()) {
            News news = optionalNews.get();
            User user = optionalUser.get();
            
            comment.setNews(news);
            comment.setCreatedBy(user);
            comment.setCreatedAt(new Date());

            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Invalid news or user.");
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}

