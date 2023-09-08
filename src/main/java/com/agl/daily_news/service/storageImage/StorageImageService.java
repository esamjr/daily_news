package com.agl.daily_news.service.storageImage;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageImageService {
  ResponseEntity<?> storeImage(MultipartFile file, Long newsId) throws IOException;

  ResponseEntity<?> loadImage(String imageId);
}
