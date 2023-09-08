package com.agl.daily_news.service.imageUpload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
@Service
public class ImageUploadService {
    private static final String UPLOAD_DIRECTORY = "/home/wibisana/Documents/BCA Training/daily_news/src/assets/news";
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        String filePath = Paths.get(UPLOAD_DIRECTORY, uniqueFileName).toString();
        Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public String getImageFilePath(String filename) {
        return Paths.get(UPLOAD_DIRECTORY, filename).toString();
    }
    public Resource loadImageAsResource(String filename) throws IOException {
    try {
        Path imagePath = Paths.get(UPLOAD_DIRECTORY).resolve(filename).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Image not found: " + filename);
        }
    } catch (MalformedURLException | FileNotFoundException ex) {
        throw new FileNotFoundException("Image not found: " + filename);
    }
}
}
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.StandardCopyOption;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;
// @Service
// public class ImageUploadService {

//     @Value("${upload.directory}")
//     private String uploadDirectory;

//     public String uploadImage(MultipartFile imageFile) throws IOException {
//         String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
//         Path uploadPath = Path.of(uploadDirectory);
//         Path filePath = uploadPath.resolve(uniqueFileName);

//         Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

//         return uniqueFileName;
//     }
// }