package com.agl.daily_news.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agl.daily_news.helper.ResponseHandler;
import com.agl.daily_news.model.Comment;
import com.agl.daily_news.service.comment.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{newsId}/{userId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long newsId,
            @PathVariable Long userId,
            @RequestBody Comment comment) {
        try {
            Comment addedComment = commentService.addComment(comment, newsId, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Comment successfully deleted");

        return ResponseEntity.ok(response);
    }


}


