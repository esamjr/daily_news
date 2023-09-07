package com.agl.daily_news.service.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

            if (!Objects.equals(user.getId(), userId)) {
                throw new RuntimeException("User ID mismatch.");
            }

            comment.setNews(news);
            comment.setCreatedBy(user);
            comment.setCreatedAt(new Date());

            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Invalid news or user.");
        }
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // Validate that the user ID from the token matches the user ID of the comment creator
            if (!Objects.equals(comment.getCreatedBy().getId(), userId)) {
                throw new RuntimeException("User ID mismatch.");
            }

            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("Invalid comment.");
        }
    }

}

