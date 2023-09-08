package com.agl.daily_news.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agl.daily_news.service.imageUpload.ImageUploadService;
import com.agl.daily_news.service.storageImage.StorageImageService;

@RestController
public class ImageController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    StorageImageService storageImageService;
    // @GetMapping("/{filename:.+}")
    // public ResponseEntity<Resource> getImage(@PathVariable String filename) {
    //     try {
    //         Resource resource = imageUploadService.loadImageAsResource(filename);

    //         return ResponseEntity.ok()
    //                 .contentType(MediaType.IMAGE_JPEG)
    //                 .body(resource);
    //     } catch (Exception e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }
    @PostMapping("/files/newsImage")
    public ResponseEntity<?> storeImage(@RequestParam(value = "file") MultipartFile file,
        @RequestParam(value = "newsId") Long newsId) throws IOException {
        return storageImageService.storeImage(file, newsId);
    }

    @GetMapping("/files/news/{imageId}")
    public ResponseEntity<?> loadImage(@PathVariable String imageId) {
        return storageImageService.loadImage(imageId);
    }
}

