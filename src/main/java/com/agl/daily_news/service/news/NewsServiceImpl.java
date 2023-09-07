package com.agl.daily_news.service.news;

import com.agl.daily_news.model.News;
import com.agl.daily_news.model.Role;
import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.CommentRepository;
import com.agl.daily_news.repository.NewsRepository;
import com.agl.daily_news.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    

    @Override
    public List<News> getAllNews() {
        return newsRepository.findNewestNews();
    }

    @Override
    public News getNewsById(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);

        if (optionalNews.isPresent()) {
            News news = optionalNews.get();
            news.setCommentIds(commentRepository.findCommentIdsByNewsId(news.getId()));
            return news;
        } else {
            return null;
        }
    }

    @Override
    public News getNewsWithComments(Long id) {
        Optional<News> optionalNews = newsRepository.findByIdWithComments(id);
        return optionalNews.orElse(null);
    }
    @Override
    public List<News> getNewsByTag(String tag) {
        return newsRepository.findByTagsName(tag);
    }
    @Override
    public News createNews(News news, Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Role userRole = user.getUserRole();

                if (userRole != null && userRole.getId() == 2) {
                    ZoneId jakartaZone = ZoneId.of("Asia/Jakarta");
                    ZonedDateTime jakartaTime = ZonedDateTime.now(jakartaZone);
                    Date publicationDate = Date.from(jakartaTime.toInstant());
                    news.setIsActive(true);
                    news.setCreatedBy(user.getId());
                    news.setPublicationDate(publicationDate);

                    return newsRepository.save(news);
                }
            }
        }
        throw new IllegalArgumentException("Anda tidak memiliki izin untuk membuat berita.");
    }


    @Override
    public News updateNews(Long id, News news, Long userId) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isPresent()) {
            News existingNews = optionalNews.get();
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Role userRole = user.getUserRole();

            if (user.getId().equals(existingNews.getCreatedBy()) || (userRole != null && userRole.getId() == 1)) {
                existingNews.setTitle(news.getTitle());
                existingNews.setContent(news.getContent());
                existingNews.setIsActive(news.getIsActive());
                return newsRepository.save(existingNews);
            } else {
                throw new IllegalArgumentException("You are not authorized to update this news.");
            }
        } else {
            throw new IllegalArgumentException("News not found");
        }
    }


    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
    @Override
    public List<News> getTrendingNews(int limit) {
        return newsRepository.findTopTrendingNews(limit);
    }
}

