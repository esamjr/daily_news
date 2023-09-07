package com.agl.daily_news.service.imageUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {
    private static final String UPLOAD_DIRECTORY = "/home/wibisana/Documents/BCA Training/daily_news/src/assets/news";
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String filePath = Paths.get(UPLOAD_DIRECTORY, uniqueFileName).toString();
        Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return filePath;
    }
}

