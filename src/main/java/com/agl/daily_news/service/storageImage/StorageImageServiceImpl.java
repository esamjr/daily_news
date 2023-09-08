package com.agl.daily_news.service.storageImage;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agl.daily_news.helper.ResponseHandler;
import com.agl.daily_news.model.ImageNews;
import com.agl.daily_news.model.News;
import com.agl.daily_news.repository.ImageNewsRepository;
import com.agl.daily_news.repository.NewsRepository;

@Service
public class StorageImageServiceImpl implements StorageImageService {
  @Autowired
  ImageNewsRepository imageNewsRepository;

  @Autowired
  NewsRepository newsRepository;

  @Override
  public ResponseEntity<?> storeImage(MultipartFile file, Long newsId) throws IOException {
        // ambil nama gambar
        String imgName = StringUtils.cleanPath(file.getOriginalFilename());

        // cari entitas berita
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NoSuchElementException("News is not found!"));

        // buatkan entitas image berita
        ImageNews imageNews = new ImageNews(imgName, file.getBytes(), news);
        imageNewsRepository.save(imageNews); // menyimpan id

        // buatkan sharedUrl (adjust this part based on your requirements)
        String sharedUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath() // localhost:9090
                .path("/files/news/")
                .path(imageNews.getId()) // id gambar
                .toUriString();

        // set sharedurl ke obj image berita
        imageNews.setSharedUrl(sharedUrl);
        imageNewsRepository.save(imageNews);

        return ResponseHandler.responseData(201, "success store image", imageNews);
  }

  @Override
  public ResponseEntity<?> loadImage(String imageId) {
      ImageNews imageNews = imageNewsRepository.findById(imageId)
              .orElseThrow(() -> new NoSuchElementException("Image is not found!"));

      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageNews.getImageName() + "\"")
              .body(imageNews.getData());
  }


}
